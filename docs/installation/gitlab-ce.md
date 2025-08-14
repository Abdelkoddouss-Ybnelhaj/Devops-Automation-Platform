# 🚀 GitLab CE Installation Manual

## 📌 Table of Contents

1. [Introduction](#introduction)  
2. [Prerequisites](#prerequisites)  
3. [Considerations Before Installation](#considerations-before-installation)  
4. [Preparation Steps](#preparation-steps)  
5. [GitLab EE Installation](#gitlab-ee-installation)  
6. [Internal DNS Configuration](#internal-dns-configuration)  
7. [SSL Configuration](#ssl-configuration)  
8. [Post-Installation Verification](#post-installation-verification)  
9. [Official Sources](#official-sources)

---

## 📝 Introduction

GitLab EE is the enterprise version of GitLab offering advanced CI/CD, security, and project management features. Proper preparation is crucial for a stable and secure installation.

🔗 [GitLab EE Overview](https://about.gitlab.com/install/)

---

## ✅ Prerequisites

| Item | Recommendation |
|---|---|
| **OS** | RHEL 8+ or CentOS 8+ |
| **RAM** | Minimum **4 GB**, recommended **8 GB+** |
| **CPU** | Minimum 2 vCPU |
| **Disk** | Minimum **20 GB** for installation + data |
| **Domain Name** | Valid FQDN resolvable by DNS |
| **Open Ports** | 22 (SSH), 80 (HTTP), 443 (HTTPS) |
| **Root/Sudo Access** | Required |

🔗 [GitLab Hardware Requirements](https://docs.gitlab.com/ee/install/requirements.html#hardware-requirements)

---

## ⚠️ Considerations Before Installation

1. **Installation type**: Omnibus package (rpm) or Docker (not recommended for production).  
2. **Valid domain name**: Either internal (`gitlab.local`) or public with split DNS.  
3. **SSL**: Let’s Encrypt only works with public domains.  
4. **Sufficient disk space on /**: Installation requires at least **2.5GB free** on `/`.  
5. **IP and DNS planning**: Know your server static IP and configure DNS before installing.  
6. **System backup**: Especially if installing on an existing server.

🔗 [GitLab Omnibus considerations](https://docs.gitlab.com/omnibus/architecture.html)

---

## 🔧 Preparation Steps

### 1. Check Disk Space

```bash
df -h /
```
✅ Ensure at least 2.5 GB free on /.

### 2. Set the Hostname
```bash
hostnamectl set-hostname gitlab.yourcompany.com
```
### 3. Update the System
```bash
sudo yum update -y
```
### 4. Configure Internal or Public DNS
```bash
nslookup gitlab.yourcompany.com
```
---

## 🛠️ GitLab EE Installation

### 1.  Install necessary dependencies
```bash
sudo yum install -y curl policycoreutils openssh-server perl
```
### 2. Start SSH if not enabled
```bash
sudo systemctl enable sshd
sudo systemctl start sshd
```
### 3. If you use a firewall, allow HTTP and SSH:
```bash
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --permanent --add-service=ssh
sudo firewall-cmd --reload
```

### 4. Add GitLab EE Official Repository
```bash
curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh | sudo bash
```

### 5. Install GitLab EE with EXTERNAL_URL
```bash
sudo EXTERNAL_URL="http://gitlab.yourcompany.com" yum install -y gitlab-ee
```
➡️ Replace http with https if SSL is configured prior to installation.

### 6. Reconfigure GitLab
```bash
sudo gitlab-ctl reconfigure
```

### 7. Check Installation Status
```bash
sudo gitlab-ctl status
```

## 🌐 Internal DNS Configuration

1. Use Windows Server DNS or BIND.
2. Create a Forward Lookup Zone for yourcompany.local.
3. Add an A record for gitlab.yourcompany.local pointing to the GitLab server IP.

## 🔒 SSL Configuration

**Internal CA-Signed Certificate (AD CS)**

1. Generate CSR + key.
2. Submit CSR to your internal CA (AD CS).
3. Install the signed cert and key in /etc/gitlab/ssl/.
4. Update external_url and reconfigure.

### Troubleshooting
- **/etc/gitlab/gitlab.rb** tp modify the external url
- **/etc/gitlab/init_password** to retrieve default password 
- **lvextend -r -L +2G /dev/rootvg/rootlv** to adjust the root (/) size