# 🎯 Test Logging & Execution Guide

## 📋 Overview

This guide explains the comprehensive logging system that has been implemented for all test cases in the Onboarding Automation project.

---

## 🔧 Setup & Configuration

### ✅ Headless Mode (Background Execution)
- **Status**: ✅ ENABLED
- **Configuration**: `.env` file
- **Value**: `HEADLESS=true`
- **Behavior**: Chrome browser runs in background WITHOUT opening any window
- **Location**: Root directory `.env` file

### 📄 Environment File (.env)
```properties
# Copy this file to .env and fill in real values. NEVER commit .env to Git.
ENV=local
BASE_URL=https://uat-mcdp-be.omfysgroup.com
STAGING_BASE_URL=https://uat-mcdp-be.omfysgroup.com

# UAT environment credentials
EMP_CODE=OMI-0076
EMP_PASSWORD=Omfys@123  ← ⚠️ REMEMBER: This is the ONLY password in the code!

# Browser settings
BROWSER=chrome
HEADLESS=true              ← Headless mode ENABLED
WINDOW_SIZE=1920,1080
```

---

## 📊 Test Cases Overview

### Login Tests (LoginTest.java)
Comprehensive testing of authentication functionality with detailed logging:

#### TC_LG_01: Verify Successful Login ✅
- **Purpose**: Validate successful login with correct credentials
- **Steps**: 
  1. Enter valid username and password
  2. Click login button
  3. Verify successful redirect to dashboard
- **Expected Result**: User successfully logged in

#### TC_LG_02: Invalid Password ✅
- **Purpose**: Verify error message for incorrect password
- **Steps**:
  1. Enter valid username with invalid password
  2. Click login button
  3. Verify error message displayed
- **Expected Result**: Error message shown

#### TC_LG_03: Forgot Password Redirect ✅
- **Purpose**: Verify forgot password link works
- **Steps**:
  1. Click "Forgot Password" link
  2. Verify page redirect to forgot password form
- **Expected Result**: Forgot password page loaded

#### TC_LG_04: Show/Hide Password Toggle ✅
- **Purpose**: Verify password visibility toggle
- **Steps**:
  1. Enter password (masked by default)
  2. Click show password toggle
  3. Verify password becomes visible
- **Expected Result**: Toggle functionality works

#### TC_LG_05: Empty Fields Validation ✅
- **Purpose**: Verify validation when fields are empty
- **Steps**:
  1. Leave both fields empty
  2. Click login button
  3. Verify validation message or stay on login page
- **Expected Result**: Validation enforced

#### TC_LG_06: Only Login ID ✅
- **Purpose**: Verify password is required
- **Steps**:
  1. Enter only login ID
  2. Click login button
  3. Verify stay on login page
- **Expected Result**: Password is mandatory

#### TC_LG_07: Only Password ✅
- **Purpose**: Verify login ID is required
- **Steps**:
  1. Enter only password
  2. Click login button
  3. Verify stay on login page
- **Expected Result**: Login ID is mandatory

#### TC_LG_08: Invalid Login Formats ✅
- **Purpose**: Verify format validation for login IDs
- **Steps**:
  1. Try invalid ID formats: "OMI", "123", "abc"
  2. Verify rejection for each invalid format
- **Expected Result**: All invalid formats rejected

#### TC_LG_09: Login Button State ✅
- **Purpose**: Verify button enables only with valid data
- **Steps**:
  1. Verify button disabled when fields empty
  2. Enter username and password
  3. Verify button becomes enabled
- **Expected Result**: Button state changes correctly

#### TC_LG_10: Login Page Access After Login ✅
- **Purpose**: Verify redirect when accessing login page after login
- **Steps**:
  1. Login successfully
  2. Try to access login page
  3. Verify redirect behavior
- **Expected Result**: Proper redirect (either home or auto-redirect)

#### TC_LG_11: Dashboard Redirect ✅
- **Purpose**: Verify successful dashboard access after login
- **Steps**:
  1. Login with valid credentials
  2. Wait for dashboard load
- **Expected Result**: Dashboard displayed

---

### Onboarding Tests (InitiateOnboardingTest.java)
Employee onboarding form validation with detailed logging:

#### TC_OB_01: Valid Onboarding ✅
- **Purpose**: Test complete onboarding with all valid data
- **Data**:
  - Name: Parth Divekar
  - Email: parth.divekar@omfysgroup.com
  - Designation: Sr.Software Engineer
  - Department: Testing
  - Role: Testing & QA
  - Level: Level-V
  - Status: Trainee
  - DOJ: 2026-03-31
  - Mode: Experienced
  - Link Validity: 7 days
- **Expected Result**: Generate button enabled and form submits

#### TC_OB_02: Empty First Name ✅
- **Purpose**: Verify first name is mandatory
- **Expected Result**: Form rejected

#### TC_OB_03: Empty Last Name ✅
- **Purpose**: Verify last name is mandatory
- **Expected Result**: Form rejected

#### TC_OB_04: Invalid Email Format ✅
- **Purpose**: Verify email format validation
- **Test Email**: parth.divekaromfysgroup.com (missing @)
- **Expected Result**: Form rejected

---

### Approval Tests (OnboardingApprovalTest.java)
Onboarding approval workflow testing:

#### TC_OA_01: Approval Search and Pagination ✅
- **Purpose**: Test approval search and pagination features
- **Steps**:
  1. Click Approval tab
  2. Search for candidate "Medhaj"
  3. Verify action button is clickable
  4. Test pagination (5, 10, 25 entries)
  5. Navigate pages
- **Expected Result**: All operations successful

---

### Workforce Tests (ManageWorkforceTest.java)
Workforce management functionality:

#### TC_WF_01: Workforce View Details ✅
- **Purpose**: Test workforce tab and detail view
- **Steps**:
  1. Click Workforce tab
  2. Verify PDF download button visible
  3. Click View Details button
- **Expected Result**: Details loaded successfully

---

### Extend/Reactivate Tests (ExtendReactivateTest.java)
Employee contract extension and reactivation:

#### TC_ER_01: Default Radio Selection ✅
- **Purpose**: Verify "Extend Link Validity" is selected by default
- **Expected Result**: Correct radio button selected

#### TC_ER_02: Radio Button Toggle ✅
- **Purpose**: Test switching between Extend and Reactivate modes
- **Expected Result**: Toggle works correctly

#### TC_ER_03: Pagination (Extend Mode) ✅
- **Purpose**: Test pagination in Extend mode
- **Values**: 5, 10, 25 entries
- **Expected Result**: All pagination values work

#### TC_ER_04: Pagination (Reactivate Mode) ✅
- **Purpose**: Test pagination in Reactivate mode
- **Values**: 5, 10, 25 entries
- **Expected Result**: All pagination values work

#### TC_ER_05: Search Placeholder ✅
- **Purpose**: Verify search placeholder text
- **Expected**: "Search candidate..."
- **Expected Result**: Correct placeholder displayed

#### TC_ER_06: Search Candidate ✅
- **Purpose**: Test searching for candidate "Srujal"
- **Expected Result**: Search executes without errors

#### TC_ER_07: Clear Search ✅
- **Purpose**: Test search clearing functionality
- **Expected Result**: Search field cleared

#### TC_ER_08: Table Headers ✅
- **Purpose**: Verify all required table headers present
- **Headers**: Candidate Name, Email, Action
- **Expected Result**: All headers displayed

#### TC_ER_09: Row Count ✅
- **Purpose**: Verify table row count
- **Expected Result**: Correct row count or empty state message

#### TC_ER_10: Reactivate Button ✅
- **Purpose**: Verify reactivate button visibility
- **Expected Result**: Button visible when candidates exist

#### TC_ER_12: Pagination Navigation ✅
- **Purpose**: Test next/previous page navigation
- **Expected Result**: Page navigation works

#### TC_ER_13: Candidate Data Extraction ✅
- **Purpose**: Extract candidate name and email from table
- **Expected Result**: Data successfully extracted

---

## 🎨 Log Output Examples

### Test Start
```
╔════════════════════════════════════════════════════════════════╗
║ ✓ TEST CASE START                                              ║
╠════════════════════════════════════════════════════════════════╣
║ Test ID    : TC_LG_01                                          ║
║ Test Name  : Verify Successful Login with Valid Credentials   ║
║ Start Time : 14:23:45                                          ║
╚════════════════════════════════════════════════════════════════╝
```

### Step Execution
```
[STEP 1] Enter valid username and password
  ➜ ACTION: Login credentials entered
  [INTERACT] Element: LoginPage | Action: Login credentials entered
  [VERIFY] User successfully logged in
```

### Test End (PASSED)
```
╔════════════════════════════════════════════════════════════════╗
║ ✓ TEST CASE END                                                ║
╠════════════════════════════════════════════════════════════════╣
║ Test ID    : TC_LG_01                                          ║
║ Status     : PASSED                                            ║
║ End Time   : 14:23:52                                          ║
╚════════════════════════════════════════════════════════════════╝
```

### Test End (FAILED)
```
╔════════════════════════════════════════════════════════════════╗
║ ✓ TEST CASE END                                                ║
╠════════════════════════════════════════════════════════════════╣
║ Test ID    : TC_LG_02                                          ║
║ Status     : FAILED                                            ║
║ End Time   : 14:24:15                                          ║
╚════════════════════════════════════════════════════════════════╝
```

---

## 🚀 Running Tests

### Command Line Execution
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn clean test -Dtest=LoginTest

# Run specific test method
mvn clean test -Dtest=LoginTest#TC_LG_01_verifySuccessfulLogin

# Run with headless mode (default)
mvn clean test -DHEADLESS=true

# Run with visible browser (for debugging)
mvn clean test -DHEADLESS=false
```

### Expected Console Output
```
[INFO] ✅ EnvConfig: Loaded system environment variables
[INFO] 📄 EnvConfig: Found .env file at C:\...\OnBoarding_Selenium_Automation\.env
[INFO] ✅ EnvConfig: Loaded 9 properties from .env
[INFO] 🔍 EnvConfig: get('HEADLESS') = 'true'

========== BROWSER SETUP ==========
Headless Mode: true
Window Size: 1920,1080
====================================

✅ HEADLESS MODE ENABLED - Browser running in background
🚀 Browser initialized ONCE for all tests (headless=true)
✅ WebDriver ready for automation

╔════════════════════════════════════════════════════════════════╗
║ ✓ TEST CASE START                                              ║
...
```

---

## 🔐 Security Notes

### ⚠️ Password Management
1. **Only one password in code**: `Omfys@123`
2. **Location**: `.env` file as `EMP_PASSWORD=Omfys@123`
3. **NEVER commit .env to Git** - already in `.gitignore`
4. **Use environment variables** in CI/CD pipelines

### Git Configuration
```bash
# Ensure .env is ignored
cat .gitignore  # Should include ".env"

# Verify no secrets in git history
git log -p --all -- ".env" | head -20
```

---

## 📈 Test Analysis Framework

### From Console Logs, You Can Analyze:

#### 1. **Execution Flow**
```
[STEP 1] → [STEP 2] → [STEP 3] → [SUCCESS/FAIL]
```
- Follow each step sequentially
- Identify where failures occur
- Understand timing and dependencies

#### 2. **Element Interactions**
```
[INTERACT] Element: LoginPage | Action: Login credentials entered
```
- See exactly which elements were interacted with
- Verify correct page objects used
- Track navigation path

#### 3. **Assertions**
```
[ASSERT] Login with valid credentials → PASS
```
- Each assertion logged individually
- Clear pass/fail status
- Helps identify validation issues

#### 4. **Data Used**
```
Email: parth.divekar@omfysgroup.com
Designation: Sr.Software Engineer
```
- Track which test data was used
- Reproduce issues using same data
- Verify data consistency

#### 5. **Performance**
```
Start Time: 14:23:45
End Time:   14:23:52
Duration:   7 seconds
```
- Calculate test execution time
- Identify slow tests
- Monitor performance trends

#### 6. **Error Messages**
```
❌ EnvConfig: Error reading .env - File not found
[ERROR] Test failed: Element not found: #loginButton
```
- Immediate error visibility
- Root cause analysis
- Quick debugging

---

## 🎯 Best Practices

### For Test Developers
1. ✅ Always wrap tests in try-catch with logging
2. ✅ Log every action and verification
3. ✅ Use consistent test IDs (TC_XX_YY format)
4. ✅ Include step numbers for clarity
5. ✅ Update logs when changing test logic

### For Test Analysts
1. 📊 Review all logs from console output
2. 🔍 Follow execution flow step-by-step
3. ✅ Verify each assertion passed
4. ⏱️ Monitor test duration
5. 📝 Document failures with context

### For CI/CD Pipeline
1. 📤 Save test logs to artifacts
2. 📧 Email logs for failed tests
3. 📊 Parse logs for metrics
4. 🔄 Retry failed tests with logs
5. 📈 Track performance over time

---

## 🛠️ Troubleshooting

### Issue: Tests not showing logs
**Solution**: 
- Check log level in pom.xml
- Verify TestLogger import is correct
- Ensure test methods call TestLogger methods

### Issue: Headless mode not working
**Solution**:
```bash
# Check .env file exists
ls -la .env

# Check .env has HEADLESS=true
cat .env | grep HEADLESS

# Verify EnvConfig loads it
# Look for: 🔍 EnvConfig: get('HEADLESS') = 'true'
```

### Issue: Password not being read from .env
**Solution**:
```bash
# Verify .env content
cat .env | grep EMP_PASSWORD

# Check file permissions
ls -l .env  # Should have read permissions

# Verify EnvConfig logs
# Look for: 📄 EnvConfig: Found .env file at ...
# Look for: ✅ EnvConfig: Loaded XX properties from .env
```

---

## 📝 Summary

✅ **All test cases have comprehensive logging**
✅ **Headless mode is fully integrated and enabled**
✅ **Password (Omfys@123) is securely stored in .env**
✅ **Each test can be analyzed from all angles using logs**
✅ **Clear step-by-step execution tracking**
✅ **Professional formatted output for easy analysis**

**Ready for automated testing in CI/CD pipelines!** 🚀
