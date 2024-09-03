import requests
from bs4 import BeautifulSoup
from difflib import SequenceMatcher

# Your Bing Search API key here
API_KEY = "YOUR_BING_SEARCH_API_KEY"

# Function to search the web using Bing Search API
def search_web(query):
    url = "https://api.bing.microsoft.com/v7.0/search"
    
    # Headers include the API key
    headers = {
        "Ocp-Apim-Subscription-Key": API_KEY
    }
    
    # Parameters include the search query and additional options
    params = {
        "q": query,                    # The search query
        "textDecorations": True,       # Decorate text with HTML tags
        "textFormat": "HTML",          # Return text in HTML format
        "count": 5                     # Limit to 5 results for brevity
    }
    
    # Making the GET request to the Bing Search API
    response = requests.get(url, headers=headers, params=params)
    
    # Raise an error if the request was unsuccessful
    response.raise_for_status()
    
    # Return the response in JSON format
    return response.json()

# Function to compare two texts and return a similarity ratio
def compare_texts(text1, text2):
    # Using SequenceMatcher to calculate the similarity ratio
    return SequenceMatcher(None, text1, text2).ratio()

# Function to check for plagiarism in the input text
def check_plagiarism(text):
    # Perform the web search using the Bing Search API
    search_results = search_web(text)
    
    max_similarity = 0
    most_similar_snippet = ""
    
    # Iterate over the search results and compare each snippet
    for result in search_results["webPages"]["value"]:
        # Extract and clean the snippet text from the search result
        snippet = BeautifulSoup(result["snippet"], "html.parser").get_text()
        
        # Calculate similarity between the user's text and the snippet
        similarity = compare_texts(text, snippet)
        
        # Track the snippet with the highest similarity
        if similarity > max_similarity:
            max_similarity = similarity
            most_similar_snippet = snippet
    
    # Return the highest similarity and the corresponding snippet
    return max_similarity, most_similar_snippet

# Main function to run the plagiarism checker
def main():
    # Get the user's input text
    user_text = input("Enter the text you want to check for plagiarism: ")
    
    # Check the input text for plagiarism
    similarity, similar_snippet = check_plagiarism(user_text)
    
    # Display the plagiarism percentage
    print(f"\nPlagiarism Percentage: {similarity * 100:.2f}%")
    
    # Display the most similar snippet if a match is found
    if similarity > 0:
        print("\nMost similar snippet found online:\n")
        print(similar_snippet)

# Entry point of the script
if __name__ == "__main__":
    main()
