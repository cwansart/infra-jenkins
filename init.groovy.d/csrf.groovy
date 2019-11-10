/**
 * Enables the CSRF Protection settings.
 */

import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins

def instance = Jenkins.get()
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
instance.save()

println "enabled CSRF token"
