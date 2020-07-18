import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd
from heatmap import heatmap, corrplot


data = pd.read_csv('met-data.csv')

corr = data.corr()
corr = pd.melt(corr.reset_index(), id_vars='index') # Unpivot the dataframe, so we can get pair of arrays for x and y
corr.columns = ['x', 'y', 'value']
heatmap(
    x=corr['x'],
    y=corr['y'],
    size=corr['value'].abs()
)

plt.figure(figsize=(10, 10))
corrplot(data.corr())

plt.show()

