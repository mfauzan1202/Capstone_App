import pandas as pd
import os
import shutil

original_path = 'original_dataset'
sub_dir = os.listdir(original_path)
sub_dir.remove('.DS_Store')

train_path = os.path.join('dataset','train')

if not os.path.isdir(train_path):
    os.makedirs(train_path)

training_labels = []

for dir in sub_dir:
    src_file_path = os.path.join(original_path, dir)
    file_list = os.listdir(src_file_path)

    for f in file_list:
        src = os.path.join(src_file_path, f)
        filename = "{}-{}".format(dir, f)
        dst = os.path.join(train_path, filename)

        shutil.copy(src, dst)

        training_labels.append([filename, dir])

df = pd.DataFrame(training_labels)
training_labels_path = os.path.join('dataset', 'training_labels.csv')
df.to_csv(training_labels_path, header=['filename', 'class'], index=False)