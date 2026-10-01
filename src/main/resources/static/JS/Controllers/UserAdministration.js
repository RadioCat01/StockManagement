(function () {
    const baseUrl = APP_CONFIG.apiBase;
    const companySection = document.getElementById('company-create-section');
    const companyOperations = document.getElementById('company-operations');
    const companySelect = document.getElementById('user-company');
    const companyRow = document.getElementById('user-company-row');
    const roleSelect = document.getElementById('user-role');
    const storeFrontSelect = document.getElementById('user-storefront');
    const usersList = document.getElementById('users-list');
    const message = document.getElementById('admin-message');
    let platformAdmin = false;
    let companies = [];
    let storeFronts = [];
    let csrfToken;

    function showMessage(text, isError) {
        message.textContent = text;
        message.className = isError ? 'text-danger mt-3' : 'text-success mt-3';
    }

    async function request(url, options) {
        const method = options && options.method ? options.method.toUpperCase() : 'GET';
        if (!/^(GET|HEAD|OPTIONS|TRACE)$/.test(method) && !csrfToken) {
            const tokenResponse = await fetch(baseUrl + '/auth/csrf', {
                credentials: 'same-origin'
            });
            if (!tokenResponse.ok) {
                throw new Error(`Unable to initialize request security (${tokenResponse.status}).`);
            }
            csrfToken = (await tokenResponse.json()).token;
        }
        const response = await fetch(baseUrl + url, {
            credentials: 'same-origin',
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...(csrfToken ? {'X-XSRF-TOKEN': csrfToken} : {}),
                ...(options && options.headers)
            }
        });
        if (!response.ok) {
            const details = await response.text();
            throw new Error(details || `Request failed (${response.status}).`);
        }
        return response.status === 204 ? null : response.json();
    }

    function addOptions(select, values, getId, getLabel, placeholder) {
        select.replaceChildren();
        const empty = document.createElement('option');
        empty.value = '';
        empty.textContent = placeholder;
        select.appendChild(empty);
        values.forEach(function (value) {
            const option = document.createElement('option');
            option.value = getId(value);
            option.textContent = getLabel(value);
            select.appendChild(option);
        });
    }

    function updateAvailableRoles() {
        const roles = platformAdmin
            ? ['COMPANY_ADMIN', 'CASHIER', 'STOCK_CLERK']
            : ['CASHIER', 'STOCK_CLERK'];
        addOptions(roleSelect, roles, function (role) {
            return role;
        }, function (role) {
            return role.replaceAll('_', ' ');
        }, 'Select a role');
        companyRow.hidden = !platformAdmin;
        companySelect.required = platformAdmin;
        updateStoreFrontVisibility();
    }

    function updateStoreFrontVisibility() {
        const needsStoreFront = roleSelect.value === 'CASHIER'
            || platformAdmin && roleSelect.value === 'STOCK_CLERK';
        storeFrontSelect.required = needsStoreFront;
        storeFrontSelect.disabled = !needsStoreFront;
        if (!needsStoreFront) {
            storeFrontSelect.value = '';
        }
    }

    async function loadUsers() {
        const users = await request('/admin/users');
        usersList.replaceChildren();
        users.forEach(function (user) {
            const row = document.createElement('tr');
            const values = [
                user.username,
                companies.find(function (company) {
                    return company.companyId === user.companyId;
                })?.companyName || '',
                (user.roles || []).join(', '),
                user.enabled ? 'Active' : 'Disabled'
            ];
            values.forEach(function (value) {
                const cell = document.createElement('td');
                cell.textContent = value;
                row.appendChild(cell);
            });
            const actions = document.createElement('td');
            if (!user.roles.includes('PLATFORM_ADMIN') && !user.roles.includes('ADMIN')) {
                const button = document.createElement('button');
                button.className = 'btn btn-sm ' + (user.enabled ? 'btn-danger' : 'btn-success');
                button.type = 'button';
                button.textContent = user.enabled ? 'Disable' : 'Enable';
                button.addEventListener('click', async function () {
                    try {
                        await request(`/admin/users/${user.id}/enabled`, {
                            method: 'PATCH',
                            body: JSON.stringify({enabled: !user.enabled})
                        });
                        await loadUsers();
                        showMessage('User status updated.', false);
                    } catch (error) {
                        showMessage(error.message, true);
                    }
                });
                actions.appendChild(button);
            }
            row.appendChild(actions);
            usersList.appendChild(row);
        });
    }

    function updateStoreFrontOptions() {
        const selectedCompanyId = Number(companySelect.value);
        const availableStoreFronts = platformAdmin && selectedCompanyId
            ? storeFronts.filter(function (storeFront) {
                return storeFront.companyId === selectedCompanyId;
            })
            : storeFronts;
        addOptions(storeFrontSelect, availableStoreFronts, function (storeFront) {
            return storeFront.storefrontId;
        }, function (storeFront) {
            return storeFront.displayName;
        }, 'Select a store front');
    }

    async function reloadCompanyResources() {
        const results = await Promise.all([
            request('/admin/companies'),
            request('/company/storeFront')
        ]);
        companies = results[0];
        storeFronts = results[1];
        addOptions(companySelect, companies, function (company) {
            return company.companyId;
        }, function (company) {
            return company.companyName;
        }, 'Select a company');
        updateStoreFrontOptions();
    }

    document.getElementById('company-create-form').addEventListener('submit', async function (event) {
        event.preventDefault();
        try {
            const created = await request('/admin/companies', {
                method: 'POST',
                body: JSON.stringify({
                    companyName: document.getElementById('new-company-name').value,
                    companyAdmin: {
                        username: document.getElementById('new-admin-username').value,
                        password: document.getElementById('new-admin-password').value
                    }
                })
            });
            event.target.reset();
            await reloadCompanyResources();
            await loadUsers();
            showMessage(`Created ${created.companyName} and its company administrator.`, false);
        } catch (error) {
            showMessage(error.message, true);
        }
    });

    document.getElementById('user-create-form').addEventListener('submit', async function (event) {
        event.preventDefault();
        const requestBody = {
            username: document.getElementById('user-username').value,
            password: document.getElementById('user-password').value,
            role: roleSelect.value,
            phoneNumber: document.getElementById('user-phone').value,
            address: document.getElementById('user-address').value
        };
        if (platformAdmin) {
            requestBody.companyId = Number(companySelect.value);
        }
        if (storeFrontSelect.value) {
            requestBody.storeFrontId = Number(storeFrontSelect.value);
        }
        try {
            await request('/admin/users', {
                method: 'POST',
                body: JSON.stringify(requestBody)
            });
            event.target.reset();
            updateStoreFrontVisibility();
            await loadUsers();
            showMessage('User account created.', false);
        } catch (error) {
            showMessage(error.message, true);
        }
    });

    roleSelect.addEventListener('change', updateStoreFrontVisibility);
    companySelect.addEventListener('change', updateStoreFrontOptions);

    request('/auth/session').then(function (session) {
        platformAdmin = (session.roles || []).includes('ROLE_PLATFORM_ADMIN');
        companyOperations.hidden = !(session.roles || []).includes('ROLE_COMPANY_ADMIN');
        companySection.hidden = !platformAdmin;
        return reloadCompanyResources().then(updateAvailableRoles);
    }).then(loadUsers).catch(function (error) {
        showMessage(error.message, true);
    });
}());
