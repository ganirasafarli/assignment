# **Application Readme**

This Readme file provides an overview of the functionality and endpoints of my application. 
The application is designed to manage and retrieve quotes based on different criteria.
Below, you will find information about the endpoints and their corresponding functionalities.

### Requirements ###
- Java 17
- Spring Boot
- Gradle

 
**Note:**
For building and running the postgresql-container in the local environment (with docker-compose): docker-compose up
## Endpoints

All endpoints in this application are prefixed with /v1/quote.



### 1. Get Random Quote
**Endpoint**: GET /v1/quote/random
**Description**: Retrieves a random quote.
**Request**: N/A
**Response**: Returns a response entity with the QuoteDTO object containing the random quote.


### 2. Search Quotes
**Endpoint**: POST /v1/quote/search
**Description**: Searches for quotes based on certain criteria.
**Request**: Accepts a SearchRequest object as the request body, which contains the search criteria.
**Response**: Returns a response entity with a Page<QuoteDTO> object containing the search results.


**Request Body**
The request body should be a JSON object of type SearchRequest.

```json
{
  "filters": [
    {
      "key": "author",
      "operator": "EQUAL",
      "value": "Sharon Salzberg",
      "values": [""]
    }
  ],
  "sorts": [
    {
      "key": "category",
      "direction": "ASC"
    }
  ],
  "page": 0,
  "size": 3
}
```

**filters** (List of FilterRequest): 
Specifies the filters to apply for the search. Each FilterRequest object represents a filter condition with the following properties:

**key** (String): The name of the column in the quote table to filter on.

**operator** (String): The operator to apply for the filter. Available operators are: EQUAL, NOT_EQUAL, LIKE, IN, and HAS.

**value** (String): The value to compare with the specified column.

**values** (List of Strings): Additional values for certain operators (e.g., IN).

**sorts** (List of SortRequest): Specifies the sorting criteria for the search results. Each SortRequest object represents a sorting condition with the following properties:

**key** (String): The name of the column in the quote table to sort by.

**direction** (String): The sorting direction. Available values are: ASC (ascending) and DESC (descending).

**page** (Integer): The page number of the search results.

**size** (Integer): The number of results to include per page.

Response
The response will be a ResponseEntity containing a Page of QuoteDTO objects.

## Request Models

### SearchRequest

The SearchRequest class represents the search criteria for the quotes.

**Properties**
**filters** (List of FilterRequest): The list of filter conditions to apply.
**sorts** (List of SortRequest): The list of sorting conditions to apply.
**page (**Integer): The page number for the search results.
**size** (Integer): The number of results to include per page.
**FilterRequest**
The FilterRequest class represents a filter condition for the search.

**Properties**
**key** (String): The name of the column in the quote table to filter on.
**operator** (Operator): The operator to apply for the filter.
**value** (String): The value to compare with the specified column.
**values** (List of Strings): Additional values for certain operators (e.g., IN).
SortRequest
The SortRequest class represents a sorting condition for the search results.

**Properties**

key (String): The name of the column in the quote table to sort by.
direction (SortDirection): The sorting direction.

### 3. Like/Dislike a Quote
   **Endpoint**: GET /like/{id}
   **Description**: Likes or dislikes a specific quote.
   **Request**: Requires the user's user_id as a request header and the id of the quote to like/dislike as a path variable.
   **Response**: Returns a response entity with the result of the like/dislike operation.



