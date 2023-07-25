Feature: Validate API
@API1
Scenario Outline: validate API response contains expected element
When I want to set URL as "public/v2/posts"
#And I set header content type as "application/json"
#And I hit the API and request method is "GET" and query parameters are "<QueryParameter1>" and "" and "" for "location_places_autocomplete"
#
#Then I try to verify the status code is "<StatusCode>"
#And I validate user is "<users>"
Examples:
| StatusCode | QueryParameter1 | users |
| 200        | id=2            | Janet |