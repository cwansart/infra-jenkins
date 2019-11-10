#!groovy

import jenkins.model.*
import hudson.security.*

def instance = Jenkins.get()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def initialPassword = UUID.randomUUID()
                 .toString()
                 .split('-')[-1..-2]
                 .join()
                 .toUpperCase()
hudsonRealm.createAccount('admin', initialPassword)
instance.setSecurityRealm(hudsonRealm)

def initialPasswordFilePath = instance.getRootDir().getAbsolutePath() + '/secrets/initialPassword'
def initialPasswordFile = new File(initialPasswordFilePath)
initialPasswordFile << initialPassword
initialPasswordFile << '\n'

echo "#########################################################################"
echo "Initial password: ${initialPassword}"
echo "Written to file: ${initialPasswordFilePath}"
echo "#########################################################################"

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()