print(__doc__)

import numpy as np
import matplotlib.pyplot as plt

from sklearn import svm, datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import plot_confusion_matrix

import pandas as pd

import random

random.seed(1927)

print('Random Forest confusion matrix by using meteorology data')

features = pd.read_csv('../met-data.csv')
features.head(5)

with pd.option_context('display.max_columns', 40):
        print(features.describe(include = 'all'))

labelsT = np.array(features['temperature'])

from sklearn.cluster import KMeans
kmeans = KMeans(n_clusters=4)
kmeans.fit(labelsT.reshape(-1,1))
y_kmeans = kmeans.predict(labelsT.reshape(-1,1))

cluster_names = ["cold", "normal", "hot", "boiling"]

featuresT = features.drop('temperature', axis = 1)
X = np.array(featuresT)
y = y_kmeans
class_names = cluster_names

# Split the data into a training set and a test set
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 42)


# Import the model we are using
from sklearn.ensemble import RandomForestClassifier
# Instantiate model with 1000 decision trees
rf = RandomForestClassifier(n_estimators = 1000, random_state = 42)
# Train the model on training data
classifier = rf.fit(X_train, y_train);

print('--------feature(variable) importance calculation-----------')
from matplotlib import pyplot

# Get numerical feature importances
feature_list = list(featuresT.columns)

importances = list(rf.feature_importances_)
# List of tuples with variable and importance
feature_importances = [(feature, round(importance, 2)) for feature, importance in zip(feature_list, importances)]
# Sort the feature importances by most important first
feature_importances = sorted(feature_importances, key = lambda x: x[1], reverse = True)
# Print out the feature and importances 
for pair in feature_importances:
	print('Variable: {:20} Importance: {}'.format(*pair))

# Use the forest's predict method on the test data
predictions = rf.predict(X_test)

#predictions = kmeans.predict(predictionsT.reshape(-1,1))

from sklearn.metrics import classification_report

print('classification_report \n', classification_report(y_test, predictions))

from sklearn.metrics import accuracy_score

print('accuracy_score: \n', accuracy_score(y_test, predictions))

# Example of a confusion matrix in Python
from sklearn.metrics import confusion_matrix

results = confusion_matrix(y_test, predictions)
print('confusion_matrix(y_test, predictions) ->  \n', results)

from sklearn.preprocessing import label_binarize
a= label_binarize(y_test, classes=[0, 1, 2, 3])
b= label_binarize(predictions, classes=[0, 1, 2, 3])

from sklearn.metrics import roc_auc_score
print('roc_auc_score(a, b):  \n', roc_auc_score(a, b))

np.set_printoptions(precision=2)

# Plot non-normalized confusion matrix
titles_options = [("Confusion matrix, without normalization", None),
                  ("Normalized confusion matrix", 'true')]

for title, normalize in titles_options:
    disp = plot_confusion_matrix(classifier, X_test, y_test,
                                 display_labels=class_names,
                                 cmap=plt.cm.Blues,
                                 normalize=normalize)
    disp.ax_.set_title(title)

    print(title)
    print(disp.confusion_matrix)

plt.show()

print('---------------feature(variable) importance Visualizations--------------------')
# Import matplotlib for plotting and use magic command for Jupyter Notebooks
import matplotlib.pyplot as plt_variables;
'exec(%matplotlib inline)'
# Set the style
plt_variables.style.use('fivethirtyeight')
# list of x locations for plotting
x_values = list(range(len(importances)))
# Make a bar chart
plt_variables.bar(x_values, importances, orientation = 'vertical')
# Tick labels for x axis
plt_variables.xticks(x_values, feature_list, rotation='vertical')
# Axis labels and title
plt_variables.ylabel('Importance'); plt_variables.xlabel('Variable'); plt_variables.title('Variable Importances');
plt_variables.show()
print('---end of execution -> operation completed successfully----')
