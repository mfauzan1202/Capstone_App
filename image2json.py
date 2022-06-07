import cv2
import json

# Read Image
filepath = 'dataset/test/pempek.jpeg'
img = cv2.imread(filepath)
img = cv2.resize(img, (150,150))
img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

# Create JSON Oject
raw = json.dumps({"instances": [img.tolist()]})

# Write JSON to file
json_file = open('sample.json', 'w')
json_file.write(raw)
json_file.close()