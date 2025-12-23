# Contributing to GMBN YouTube Channel

Thank you for your interest in contributing to this project!

## GitHub Contribution Graph Troubleshooting

If you've rewritten commit history but your GitHub contribution graph is not updating, follow these steps:

### Requirements for Contributions to Appear on Your Graph

1. **Verified Email Address**
   - The email address used in your commits must be verified in your GitHub account settings
   - Go to [GitHub Email Settings](https://github.com/settings/emails) to verify your email
   - Run `git config user.email` to check which email you're using for commits

2. **Commit to the Default Branch**
   - Commits must be made to the repository's default branch (usually `master` or `main`)
   - Commits on other branches only count when they are merged into the default branch

3. **Push Rewritten History**
   - After rewriting commit history (using `git rebase` or `git filter-branch`), you must force-push:
     ```bash
     git push --force origin <branch-name>
     ```

4. **Repository Ownership**
   - You must be the owner of the repository, or
   - The repository must not be a fork (contributions to forks only count when merged into the parent repository)

### Common Issues

- **Old commits not appearing**: GitHub's contribution graph only shows activity from the past year
- **Delay in updating**: GitHub may take some time to re-index commits after a force-push
- **Private repositories**: Enable "Private contributions" in your [profile settings](https://github.com/settings/profile) to show contributions from private repos

### Setting Up Your Git Configuration

To ensure future commits are properly attributed:

```bash
# Set your name and email globally
git config --global user.name "Your Name"
git config --global user.email "your-verified-email@example.com"

# Verify your settings
git config user.name
git config user.email
```

## Making Contributions

1. Fork the repository
2. Create a new branch for your feature or fix
3. Make your changes
4. Test your changes thoroughly
5. Submit a pull request

## Code Style

Please follow the existing code style and conventions used in the project.
