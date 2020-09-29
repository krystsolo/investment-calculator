pipeline {
    agent none
    stages {
        stage('Build') {
            agent { docker 'openjdk:11-jdk' }
            steps {
                sh './mvnw clean package surefire-report:report-only'
            }
            post {
                always {
                    script {
                        junit '**/target/surefire-reports/TEST-*.xml'
                    }
                }
            }
        }
        stage('Sonar') {
            when { branch 'master' }
            agent { docker 'fintech/sonar-agent' }
            steps {
                withSonarQubeEnv('SonarQube') {
                    script {
                        sh "sonar-scanner -Dsonar.projectKey=ersa-team::investment-calculator -Dsonar.java.binaries=./target/classes"
                    }
                }
            }
        }
        stage('Docker push') {
            when { branch 'master' }
            agent none
            steps {
                script {
                    docker.withRegistry('https://ersa-team-docker-registry.fintechchallenge.pl/v2/', 'docker-push-user') {
                        def build = docker.build("ersa-team/investment-calculator")
                        build.push("latest")
                    }
                }
            }
        }
        stage('Deploy Sit') {
            when { branch 'master' }
            agent { docker 'fintech/kubernetes-agent' }
            steps {
                script {
                    withCredentials([file(credentialsId: 'kubeconfig-sit', variable: 'KUBECONFIG')]) {
                        sh "kubectl apply -f ./kubernetes-sit.yaml"
                        sh "kubectl rollout restart deployment investment-calculator"
                    }
                }
            }
        }
        stage('Deploy Uat') {
            when { branch 'master' }
            agent { docker 'fintech/kubernetes-agent' }
            steps {
                script {
                    withCredentials([file(credentialsId: 'kubeconfig-uat', variable: 'KUBECONFIG')]) {
                        sh "kubectl apply -f ./kubernetes-uat.yaml"
                        sh "kubectl rollout restart deployment investment-calculator"
                    }
                }
            }
        }
    }
}