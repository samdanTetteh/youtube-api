# Contributing to GMBN YouTube Channel

Thank you for your interest in contributing to this project!

## Setting Up Git for Proper Attribution

For your commits to appear in your GitHub profile activity tiles (contribution graph), make sure your Git configuration uses an email address that is verified in your GitHub account.

### Check your current Git configuration:

```bash
git config user.email
git config user.name
```

### Configure Git with your GitHub-verified email:

```bash
git config --global user.email "your-github-verified-email@example.com"
git config --global user.name "Your Name"
```

### Important Notes:

1. **Email must be verified on GitHub**: The email address used in your commits must be added and verified in your [GitHub email settings](https://github.com/settings/emails).

2. **Using GitHub's noreply email**: If you want to keep your email private, you can use GitHub's noreply email format:
   - Go to https://github.com/settings/emails
   - Enable "Keep my email addresses private"
   - Use the provided noreply email: `username@users.noreply.github.com` or `ID+username@users.noreply.github.com`

3. **Fixing past commits**: If you've already made commits with a different email, you can add that email to your GitHub account to have those commits attributed to you.

### Why commits might not show in your profile:

- The email address in your commits doesn't match any verified email on your GitHub account
- The repository is not being counted (must be a public repo or a private repo you own)
- Commits were made on a fork (only count when merged to the default branch)
- Commits were made on a branch other than the default branch

For more information, see [GitHub's documentation on contribution graph](https://docs.github.com/en/account-and-profile/setting-up-and-managing-your-github-profile/managing-contribution-settings-on-your-profile/why-are-my-contributions-not-showing-up-on-my-profile).
