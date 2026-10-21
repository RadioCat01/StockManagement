pipeline {
  agent { label 'stock-agent' }

  options {
    timeout(time: 10, unit: 'MINUTES')
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  stages {
    stage('Checkout Info') {
      steps {
        sh '''
          echo "Branch/commit:"
          git log -1 --pretty=format:"%h %an: %s"
          echo
        '''
      }
    }

    stage('Agent Info') {
      steps {
        sh '''
          echo "Node:      $NODE_NAME"
          echo "Host:      $(hostname)"
          echo "User:      $(whoami)"
          echo "Workspace: $WORKSPACE"
          java -version
        '''
      }
    }

    stage('Smoke Test') {
      steps {
        sh 'echo "Pipeline is working" > result.txt && cat result.txt'
      }
    }
  }

  post {
    success { echo 'Build succeeded' }
    failure { echo 'Build failed' }
    always  { cleanWs() }
  }
}