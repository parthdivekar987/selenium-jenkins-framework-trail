# ⚡ Quick Reference Card

## 🔑 Key Information

| Item | Value | Notes |
|------|-------|-------|
| **Headless Mode** | ✅ ENABLED | Chrome runs in background |
| **Password** | `Omfys@123` | ONLY password in code, stored in `.env` |
| **Employee Code** | `OMI-0076` | In `.env` as `EMP_CODE` |
| **Base URL** | `https://uat-mcdp-be.omfysgroup.com` | In `.env` as `BASE_URL` |
| **Test Framework** | TestNG | Using `@Test` annotations |
| **Browser** | Chrome | Via WebDriverManager |
| **Environment** | Java 17 | Configured in pom.xml |

---

## 🚀 Quick Start

### 1. **Verify Environment Setup**
```bash
# Check .env exists and has correct values
cat .env
# Should show:
# HEADLESS=true
# EMP_CODE=OMI-0076
# EMP_PASSWORD=Omfys@123
# BASE_URL=https://uat-mcdp-be.omfysgroup.com
```

### 2. **Run All Tests**
```bash
mvn clean test
```

### 3. **Run Specific Test Class**
```bash
mvn clean test -Dtest=LoginTest
```

### 4. **Run Single Test**
```bash
mvn clean test -Dtest=LoginTest#TC_LG_01_verifySuccessfulLogin
```

---

## 📊 Test Summary

### Login Tests (11 tests)
- TC_LG_01: ✅ Valid Login
- TC_LG_02: ✅ Invalid Password
- TC_LG_03: ✅ Forgot Password
- TC_LG_04: ✅ Show Password Toggle
- TC_LG_05: ✅ Empty Fields
- TC_LG_06: ✅ Only Login ID
- TC_LG_07: ✅ Only Password
- TC_LG_08: ✅ Invalid Formats
- TC_LG_09: ✅ Button State
- TC_LG_10: ✅ Login Page Access After Login
- TC_LG_11: ✅ Dashboard Redirect

### Onboarding Tests (4 tests)
- TC_OB_01: ✅ Valid Onboarding
- TC_OB_02: ✅ Empty First Name
- TC_OB_03: ✅ Empty Last Name
- TC_OB_04: ✅ Invalid Email

### Approval Tests (1 test)
- TC_OA_01: ✅ Search & Pagination

### Workforce Tests (1 test)
- TC_WF_01: ✅ View Details

### Extend/Reactivate Tests (10 tests)
- TC_ER_01: ✅ Default Radio Selection
- TC_ER_02: ✅ Radio Button Toggle
- TC_ER_03: ✅ Pagination (Extend)
- TC_ER_04: ✅ Pagination (Reactivate)
- TC_ER_05: ✅ Search Placeholder
- TC_ER_06: ✅ Search Candidate
- TC_ER_07: ✅ Clear Search
- TC_ER_08: ✅ Table Headers
- TC_ER_09: ✅ Row Count
- TC_ER_10: ✅ Reactivate Button
- TC_ER_12: ✅ Pagination Navigation
- TC_ER_13: ✅ Data Extraction

**Total: 27 Test Cases** ✅

---

## 🎨 Log Level Examples

### Test Starts
```
╔════════════════════════════════════════════════════════════════╗
║ ✓ TEST CASE START                                              ║
║ Test ID    : TC_LG_01                                          ║
║ Test Name  : Verify Successful Login with Valid Credentials   ║
║ Start Time : 14:23:45                                          ║
╚════════════════════════════════════════════════════════════════╝
```

### Steps
```
[STEP 1] Enter valid username and password
  ➜ ACTION: Login credentials entered
  ✓ VERIFY: User successfully logged in
```

### Test Ends
```
╔════════════════════════════════════════════════════════════════╗
║ ✓ TEST CASE END                                                ║
║ Status     : PASSED                                            ║
║ End Time   : 14:23:52                                          ║
╚════════════════════════════════════════════════════════════════╝
```

---

## 🔧 Headless Mode Control

### Enable Headless (Default)
```
.env: HEADLESS=true
```
➜ **Browser runs in background, NO window visible**

### Disable Headless (Debug)
```
.env: HEADLESS=false
```
➜ **Browser opens visible window for debugging**

---

## 🔐 Security Checklist

✅ `.env` file in `.gitignore`
✅ Password only in `.env` (not in code)
✅ No hardcoded credentials
✅ EnvConfig logs password masking (optional feature)
✅ CI/CD should use environment variables

---

## 📈 Reading Logs from TestLogger

| Log Type | Format | Example |
|----------|--------|---------|
| Test Start | Headers | `╔════════════════════╗` |
| Step | `[STEP N]` | `[STEP 1] Enter credentials` |
| Action | `➜ ACTION` | `➜ ACTION: Login clicked` |
| Verify | `✓ VERIFY` | `✓ VERIFY: User logged in` |
| Info | `[INFO]` | `[INFO] Search placeholder text` |
| Success | `[SUCCESS]` | `[SUCCESS] Test completed` |
| Error | `[ERROR]` | `[ERROR] Element not found` |
| Warning | `[WARNING]` | `[WARNING] No data found` |
| Interact | `[INTERACT]` | `[INTERACT] Element: Button \| Action: clicked` |

---

## 🎯 Test Data

### Login Credentials
```
Username: OMI-0076
Password: Omfys@123
```

### Onboarding Test Data
```
First Name: Parth
Last Name: Divekar
Email: parth.divekar@omfysgroup.com
Designation: Sr.Software Engineer
Department: Testing
Role: Testing & QA
Level: Level-V
Status: Trainee
DOJ: 2026-03-31
Mode: Experienced
Link Validity: 7 days
```

### Search Test Data
```
Candidate: Medhaj (for approval tests)
Candidate: Srujal (for extend/reactivate)
```

---

## 🚨 Common Issues & Solutions

### Issue: Logs not showing
✅ **Solution**: Ensure all test methods are importing `TestLogger`

### Issue: Headless mode not working
✅ **Solution**: Verify `.env` has `HEADLESS=true`

### Issue: Tests fail with "Element not found"
✅ **Solution**: 
- Wait times increased to 20 seconds
- Check element selectors in page objects
- Verify page loaded before interaction

### Issue: Password errors
✅ **Solution**: Verify `.env` has `EMP_PASSWORD=Omfys@123`

---

## 📝 File Structure

```
OnBoarding_Selenium_Automation/
├── .env                          ← Configuration file
├── TEST_LOGGING_GUIDE.md         ← Full documentation
├── QUICK_REFERENCE.md            ← This file
├── pom.xml                        ← Maven config
├── testng.xml                     ← TestNG config
├── src/test/java/com/onboarding/automation/
│   ├── base/
│   │   ├── BaseTest.java         ← Browser setup & teardown
│   │   └── EnvConfig.java        ← Environment loader
│   ├── utils/
│   │   ├── TestLogger.java       ← Logging utility ← NEW!
│   │   └── ScreenshotUtil.java
│   ├── tests/
│   │   ├── LoginTest.java        ← 11 login tests
│   │   ├── InitiateOnboardingTest.java  ← 4 onboarding tests
│   │   ├── OnboardingApprovalTest.java  ← 1 approval test
│   │   ├── ManageWorkforceTest.java     ← 1 workforce test
│   │   ├── ExtendReactivateTest.java    ← 10 ER tests
│   │   └── leave/              ← Additional modules
│   └── pages/                    ← Page objects
└── screenshots/                  ← Test screenshots
```

---

## ✨ What's New

### TestLogger.java (NEW)
Professional logging utility with:
- ✅ Colored console output
- ✅ Formatted headers
- ✅ Step-by-step tracking
- ✅ Assertion logging
- ✅ Time tracking
- ✅ Error/Warning/Success messages

### Enhanced Test Cases
- ✅ Every test uses TestLogger
- ✅ Detailed step logging
- ✅ Element interaction tracking
- ✅ Verification logs
- ✅ Error handling with logs

### Improved EnvConfig.java
- ✅ Detailed debug logs
- ✅ File location reporting
- ✅ Property count display
- ✅ Error stack traces

---

## 🎓 Analysis Tips

### Analyzing Test Execution
1. **Copy console output** to text file
2. **Search for test ID** (e.g., "TC_LG_01")
3. **Follow [STEP] entries** sequentially
4. **Check all [VERIFY] entries** for pass
5. **Look for [ERROR] entries** if test fails

### Debugging Test Failures
1. Find `[ERROR]` in logs
2. Look at `[STEP X]` before error
3. Check `[INTERACT]` for element used
4. Verify test data in logs
5. Compare with expected vs actual

### Performance Analysis
1. Note test `Start Time`
2. Note test `End Time`
3. Calculate duration
4. Compare with baseline
5. Identify slow tests

---

**Last Updated**: 2026-05-20
**Status**: ✅ Production Ready
**Coverage**: 27 Test Cases
**Mode**: Headless ✅
