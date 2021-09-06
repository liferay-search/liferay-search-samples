# $1 is the liferay site ID, $2 is the global site ID--both are now found in site settings > site configuration (7.4.2-ga3)

if [ "${#}" -ne 2 ]
then
	echo "ERROR"
	echo "You must pass 2 arguments: the site ID of the default site (e.g., 20125), and the site ID of the global site (e.g., 20127)."
	echo "To find these IDs, navigate to each site's Site Settings > Site Configuration screen."
	exit
fi

# Add a root level document
curl \
	-F "document={\"description\": \"Baker\", \"title\": \"Able Document\"}" \
	-F "file=@test.txt" \
	-H "Content-Type: multipart/form-data" \
	-X POST \
	"http://localhost:8080/o/headless-delivery/v1.0/sites/${1}/documents" \
	-u "test@liferay.com:test"

# Add the Document Folder
curl \
	-H "Content-Type: application/json" \
	-X POST \
	"http://localhost:8080/o/headless-delivery/v1.0/sites/${1}/document-folders" \
	-d "{\"name\": \"Able Document Folder\"}" \
	-u "test@liferay.com:test"

echo "Enter the ID:"
read documentFolderId

# Add the Document inside the folder
curl \
	-F "document={\"description\": \"Baker\", \"title\": \"Able Document\"}" \
	-F "file=@test.txt" \
	-H "Content-Type: multipart/form-data" \
	-X POST "http://localhost:8080/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents" \
	-u "test@liferay.com:test"

# content folder, basic content in folder, root basic content

# Get the basic web content structure ID
curl \
	"http://localhost:8080/o/headless-delivery/v1.0/sites/${2}/content-structures?search=Basic%20Web%20Content" \
	-u "test@liferay.com:test"

echo "Enter the ID:"
read contentStructuredId

# Add a root level basic web content article
curl \
	-H "Content-Type: application/json" \
	-X POST \
	"http://localhost:8080/o/headless-delivery/v1.0/sites/${1}/structured-contents" \
	-d "{\"contentFields\": [{\"contentFieldValue\": {\"data\": \"<p>Foo</p>\"}, \"name\": \"content\"}], \"contentStructureId\": \"${contentStructuredId}\", \"title\": \"Able Article\"}" \
	-u "test@liferay.com:test"

# Add a web content folder
curl \
	-H "Content-Type: application/json" \
	-X POST \
	"http://localhost:8080/o/headless-delivery/v1.0/sites/${1}/structured-content-folders" \
	-d "{\"description\": \"Foo\", \"name\": \"Able Folder\"}" \
	-u "test@liferay.com:test"

echo "Enter the ID:"
read structuredContentFolderId

# Add a basic web content article to the folder
curl \
	-H "Content-Type: application/json" \
	-X POST \
	"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents" \
	-d "{\"contentFields\": [{\"contentFieldValue\": {\"data\": \"<p>Foo</p>\"}, \"name\": \"content\"}], \"contentStructureId\": \"${contentStructuredId}\", \"title\": \"Able Article\"}" \
	-u "test@liferay.com:test"