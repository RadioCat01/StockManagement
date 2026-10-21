pipeline {
  agent { label 'stock-agent' }

  options {
    timeout(time: 20, unit: 'MINUTES')
    timestamps()
    disableConcurrentBuilds()
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  environment {
    SERVICE    = 'stockmgt'
    DEPLOY_WAR = '/home/ubuntu/Stockmgt-2.7.18.war'
    APP_URL    = 'http://localhost:8080/'
  }

  stages {
    stage('Build & Test') {
      steps {
        sh '''
          if [ -x ./mvnw ]; then ./mvnw -B clean package
          else mvn -B clean package; fi
        '''
      }
      post {
        always { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' }
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts artifacts: 'target/*.war', fingerprint: true
      }
    }

    stage('Deploy') {
      when { expression { env.GIT_BRANCH == 'origin/19thJune' } }
      steps {
        sh '''
          set -e
          NEW_WAR=$(ls target/*.war | grep -v '\\.original$' | head -n 1)
          echo "Deploying $NEW_WAR"

          # Back up the current version for rollback
          [ -f "$DEPLOY_WAR" ] && cp -p "$DEPLOY_WAR" "$DEPLOY_WAR.bak"

          cp "$NEW_WAR" "$DEPLOY_WAR"
          sudo systemctl restart $SERVICE
        '''
      }
    }

    stage('Health Check') {
      when { expression { env.GIT_BRANCH == 'origin/19thJune' } }
      steps {
        sh '''
          for i in $(seq 1 30); do
            CODE=$(curl -s -o /dev/null -w "%{http_code}" "$APP_URL" || true)
            if systemctl is-active --quiet $SERVICE && [ "$CODE" != "000" ] && [ "${CODE#5}" = "$CODE" ]; then
              echo "App is up (HTTP $CODE)"; exit 0
            fi
            echo "Waiting for app... ($i/30)"; sleep 5
          done
          echo "Health check failed"; exit 1
        '''
      }
    }
  }

  post {
    failure {
      script {
        sh '''
          if [ -f "$DEPLOY_WAR.bak" ] && [ "$DEPLOY_WAR" -nt "$DEPLOY_WAR.bak" ]; then
            echo "Rolling back to previous WAR"
            cp -p "$DEPLOY_WAR.bak" "$DEPLOY_WAR"
            sudo systemctl restart $SERVICE
          fi
        '''
      }
    }
    success { echo 'Build and deploy succeeded' }
    always  { cleanWs() }
  }
}