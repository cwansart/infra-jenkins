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

def initialPasswordFile = new File(instance.getRootDir().getAbsolutePath() + '/secrets/initialPassword')
initialPasswordFile << initialPassword
initialPasswordFile << '\n'

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()