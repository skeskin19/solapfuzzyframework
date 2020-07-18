print(__doc__)

import numpy as np
import matplotlib.pyplot as plt

from sklearn import svm, datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import plot_confusion_matrix

import pandas as pd

import random

import datetime
 
currentDT = datetime.datetime.now()
print ('start time:', str(currentDT))

random.seed(1927)

print('SVM confusion matrix by using meteorology data')

features = pd.read_csv('../met-data.csv')
features.head(5)

labelsT = np.array(features['sunshine_hour'])

from sklearn.cluster import KMeans
kmeans = KMeans(n_clusters=4)
kmeans.fit(labelsT.reshape(-1,1))
y_kmeans = kmeans.predict(labelsT.reshape(-1,1))

cluster_names = ["low", "normal", "high", "luminous"]

featuresT = features.drop('sunshine_hour', axis = 1)
X = np.array(featuresT)
y = y_kmeans
class_names = cluster_names

# Split the data into a training set and a test set
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 42)

# Run classifier, using a model that is too regularized (C too low) to see
# the impact on the results

model = svm.SVC(kernel='linear', C=0.01)
classifier = model.fit(X_train, y_train)


print('--------feature(variable) importance calculation-----------')
from matplotlib import pyplot as plt

# Get numerical feature importances
def f_importances(coef, names, top=-1):
    imp = coef
    imp, names = zip(*sorted(list(zip(imp, names))))

    # Show all features
    if top == -1:
        top = len(names)

    plt.barh(range(top), imp[::-1][0:top], align='center')
    plt.yticks(range(top), names[::-1][0:top])
    plt.show()

feature_list = list(featuresT.columns)

f_importances(abs(model.coef_[0]), feature_list, top=10)

#make prediction with trained model
predictions = model.predict(X_test)

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

from sklearn.metrics import multilabel_confusion_matrix
mcm=multilabel_confusion_matrix(a, b)
print('multilabel_confusion_matrix(a, b):  \n', mcm)
tn = mcm[:, 0, 0]
tp = mcm[:, 1, 1]
fn = mcm[:, 1, 0]
fp = mcm[:, 0, 1]
recall=tp / (tp + fn)
specificity=tn / (tn + fp)
fall_out=fp / (fp + tn)
miss_rate=fn / (fn + tp)
print('recall:  \n', recall)

from sklearn.model_selection import cross_validate
from sklearn.metrics import make_scorer

def tn(a, b): return confusion_matrix(a, b)[0, 0]
def fp(a, b): return confusion_matrix(a, b)[0, 1]
def fn(a, b): return confusion_matrix(a, b)[1, 0]
def tp(a, b): return confusion_matrix(a, b)[1, 1]
scoring = {'tp': make_scorer(tp), 'tn': make_scorer(tn), 'fp': make_scorer(fp), 'fn': make_scorer(fn)}

cv_results = cross_validate(classifier, X_train, y_train, cv=5, scoring=scoring)
print('cross_validate result:  \n', cv_results)

from sklearn.metrics import precision_recall_curve
from sklearn.metrics import plot_precision_recall_curve
import matplotlib.pyplot as plt

y_score = classifier.decision_function(X_test)
from sklearn.metrics import average_precision_score
average_precision = average_precision_score(y_test, y_score)

print('Average precision-recall score: {0:0.2f}'.format(
      average_precision))

disp = plot_precision_recall_curve(classifier, X_test, y_test)
disp.ax_.set_title('2-class Precision-Recall curve: '
                   'AP={0:0.2f}'.format(average_precision))

from sklearn.metrics import roc_curve
fpr, tpr, thresholds = roc_curve(a, b, pos_label=2)
fpr
tpr
thresholds

currentDT = datetime.datetime.now()
print ('end time:', str(currentDT))

