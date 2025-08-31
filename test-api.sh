#!/bin/bash

# REST API Test Script for Bazen Management System
# Make sure the application is running on localhost:8080

BASE_URL="http://localhost:8080/api"

echo "=== Bazen Management System API Test ==="
echo "Base URL: $BASE_URL"
echo

# Test 1: Get all pools
echo "1. Testing GET /api/bazeni (Get all pools)"
curl -s -X GET "$BASE_URL/bazeni" | python3 -m json.tool
echo
echo "---"

# Test 2: Get active pools
echo "2. Testing GET /api/bazeni/aktivni (Get active pools)"
curl -s -X GET "$BASE_URL/bazeni/aktivni" | python3 -m json.tool
echo
echo "---"

# Test 3: Get all members
echo "3. Testing GET /api/clanovi (Get all members)"
curl -s -X GET "$BASE_URL/clanovi" | python3 -m json.tool
echo
echo "---"

# Test 4: Create a new member
echo "4. Testing POST /api/clanovi (Create new member)"
curl -s -X POST "$BASE_URL/clanovi" \
  -H "Content-Type: application/json" \
  -d '{
    "ime": "Petar",
    "prezime": "Marković",
    "email": "petar.markovic@test.com",
    "telefon": "+381605678901",
    "statusClanarine": "AKTIVNO"
  }' | python3 -m json.tool
echo
echo "---"

# Test 5: Create a successful reservation
echo "5. Testing POST /api/rezervacije/create (Create reservation - should succeed)"
FUTURE_DATE=$(date -d "tomorrow 14:00" '+%Y-%m-%dT%H:%M:%S')
curl -s -X POST "$BASE_URL/rezervacije/create?clanId=1&bazenId=2&datumVreme=${FUTURE_DATE}&brojOsoba=5" \
  -H "Content-Type: application/json" | python3 -m json.tool
echo
echo "---"

# Test 6: Get general report
echo "6. Testing GET /api/izvestaji/opsti (Get general report)"
curl -s -X GET "$BASE_URL/izvestaji/opsti" | python3 -m json.tool
echo
echo "---"

# Test 7: Get maintenance for pool
echo "7. Testing GET /api/odrzavanje (Get all maintenance)"
curl -s -X GET "$BASE_URL/odrzavanje" | python3 -m json.tool
echo
echo "---"

echo "=== API Testing Complete ==="
echo
echo "WARNING: To test the critical capacity validation (app shutdown),"
echo "create a reservation that exceeds the pool capacity:"
echo "curl -X POST \"$BASE_URL/rezervacije/create?clanId=1&bazenId=2&datumVreme=${FUTURE_DATE}&brojOsoba=25\""
echo
echo "This will cause the application to log the error and shut down!"