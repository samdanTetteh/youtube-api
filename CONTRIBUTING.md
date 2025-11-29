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

4. **Rewriting commit history (when old email is inaccessible)**: If you can no longer access the old email to verify it, you can rewrite the commit history to use your new verified email.

   **Option A: Using git-filter-repo (recommended)**
   
   First, install [git-filter-repo](https://github.com/newren/git-filter-repo):
   ```bash
   pip install git-filter-repo
   ```
   
   Then create a `.mailmap` file in your repo with:
   ```
   New Name <new-verified-email@example.com> <old-email@example.com>
   ```
   
   Run the filter:
   ```bash
   git filter-repo --mailmap .mailmap
   ```

   **Option B: Using git filter-branch (legacy)**
   
   ```bash
   git filter-branch --env-filter '
   OLD_EMAIL="old-email@example.com"
   NEW_EMAIL="your-verified-email@example.com"
   NEW_NAME="Your Name"
   
   if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]; then
       export GIT_COMMITTER_EMAIL="$NEW_EMAIL"
       export GIT_COMMITTER_NAME="$NEW_NAME"
   fi
   if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]; then
       export GIT_AUTHOR_EMAIL="$NEW_EMAIL"
       export GIT_AUTHOR_NAME="$NEW_NAME"
   fi
   ' --tag-name-filter cat -- --branches --tags
   ```
   
   > **Note**: `git filter-branch` is deprecated. Use `git-filter-repo` when possible.

   After running either option, force push to update the remote:
   ```bash
   git push --force-with-lease --tags origin 'refs/heads/*'
   ```

   **⚠️ Warning**: This rewrites history and requires a force push. Coordinate with collaborators before doing this on shared branches.

### Why commits might not show in your profile:

- The email address in your commits doesn't match any verified email on your GitHub account
- The repository is not being counted (must be a public repo or a private repo you own)
- Commits were made on a fork (only count when merged to the default branch)
- Commits were made on a branch other than the default branch

For more information, see [GitHub's documentation on contribution graph](https://docs.github.com/en/account-and-profile/setting-up-and-managing-your-github-profile/managing-contribution-settings-on-your-profile/why-are-my-contributions-not-showing-up-on-my-profile).
