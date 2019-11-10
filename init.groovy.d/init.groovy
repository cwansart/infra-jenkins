/**
 * Creates a default account "admin" with a random password and disables full
 * access for non-logged in users.
 */

import jenkins.model.Jenkins
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy

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

println '######################################################################'
println "Initial password: ${initialPassword}"
println "Written to file: ${initialPasswordFilePath}"
println '#######################################################################'

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)
instance.setNoUsageStatistics(true)
instance.save()