# JUnit Create/Update & GitHub Upload Automation

## Purpose
Automates the process of:
1. Creating new JUnit test cases from a Java implementation file.
2. Updating existing JUnit test cases with complete coverage.
3. Uploading the updated source and test files to a specified GitHub repository folder.

## Agents Used
- **junity** → Generates complete JUnit test cases for a given Java file.
- **junit_update** → Updates existing JUnit test cases while keeping old tests intact.
- **git_up** → Uploads files to a GitHub repository.

## Workflow Steps
### Step 1: Input Files
- User uploads:
  - Java implementation file (`*.java`)
  - Existing JUnit test file (`*.java`) — optional if creating from scratch.

### Step 2: Create/Update JUnit Tests
- If creating new tests → **junity** generates full JUnit 5 test coverage.
- If updating existing tests → **junit_update** merges new test cases with existing ones.
- All service calls are **mocked** for isolation and faster execution.
- Tests include:
  - Getter validations
  - Service method mocks
  - Child node list handling
  - `getExportedType()` verification

### Step 3: Upload to GitHub
- Files are placed in a **new folder** (e.g., `Junit_Test`) in the target repo.
- Upload is handled by **git_up** using a **secure stored GitHub token** (no repeated prompts).
- Branch: `main` (configurable).

## Security Notes
- GitHub token is stored securely and never displayed in logs.
- Token is reused for future runs without re-prompting.
- Workflow is private and only accessible to authorized users.

## Example Repo Structure After Upload
```
Junit_Test/
├── AddToCartOptModelImpl.java
├── AddToCartOptModelImplTest.java
└── JUnit_Create_Update_GitHub_Automation.md
```

## Benefits
- Saves time by automating test creation and updates.
- Ensures complete coverage with mocked dependencies.
- Seamless integration with GitHub for version control.
- Secure handling of private repository credentials.