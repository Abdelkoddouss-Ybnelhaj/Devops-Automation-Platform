import jenkins.model.*
import hudson.util.Secret
import hudson.tasks.Mailer
import com.cloudbees.plugins.credentials.CredentialsProvider
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials

// ====================================================
// 🔑 Replace with your credential ID
def credentialsId = "jenkins_smtp_credentials"

// ✅ Retrieve Jenkins credentials (username & password)
def creds = CredentialsProvider.lookupCredentials(
    StandardUsernamePasswordCredentials.class,
    Jenkins.instance,
    null,
    null
).find { it.id == credentialsId }

if (creds == null) {
    println("❌ Credentials with ID '${credentialsId}' not found!")
    return
}

// ====================================================
// ✅ Configure Basic Mailer Plugin
def mailer = Jenkins.instance.getDescriptorByType(Mailer.DescriptorImpl)
mailer.smtpHost = "smtp.gmail.com"
mailer.smtpPort = "465"
mailer.useSsl = true
mailer.useTls = false
mailer.smtpAuthUsername = creds.username
mailer.smtpAuthPassword = Secret.fromString(creds.password)
mailer.replyToAddress = creds.username
mailer.charset = "UTF-8"
mailer.save()

println("✅ Basic Mailer Plugin configured successfully.")

// ====================================================
// ✅ Configure Email Extension Plugin
def emailExt = Jenkins.instance.getDescriptorByType(hudson.plugins.emailext.ExtendedEmailPublisherDescriptor)

emailExt.smtpHost = "smtp.gmail.com"
emailExt.smtpPort = "465"
emailExt.useSsl = true
emailExt.smtpUsername = creds.username
emailExt.smtpPassword = creds.password
emailExt.charset = "UTF-8"
emailExt.save()

println("✅ Email Extension Plugin configured successfully with credentials '${credentialsId}'.")
