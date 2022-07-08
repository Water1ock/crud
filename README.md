# crud
# A basic spring boot application that supports CRUD operations for a database.

# Columns in Database:
# 1. String Provider Name (Primary Key)
# 2. String Flow Name
# 3. LocalDateTime DownFrom
# 4. LocalDateTime DownTo

# Operations Supported:

# Create Provider - creates a new entry in the database taking provider name and flow name as request parameters,
# and the other two inputs from request body (JSON format).

# Get Provider - Two APIs for the Get request exist. One allows to retrieve all the entries in the database, and the other 
# retrieves the provider by provider name as Path Variable.

# Update Provider - allows user to update any entry by searching through the Provider Name as Path Variable.

# Delete Provider - Deletes the entry based on the provider name written after the base-url as a Path Variable.

# base-url: "/api/v1/provider"
