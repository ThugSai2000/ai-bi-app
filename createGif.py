"""
Python file to plot the results of tournaments; Python 2 or 3
"""

from functools import reduce
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.animation import FuncAnimation



def gather_data():

    xs = []
    ys = []

    tempX = []
    tempY = []

    with open('geneticRes.txt', 'r') as fromFile:

        for line in fromFile:
            if line != '-------\n':
                l = line.strip().split(' ')
                tempX.append(l[0])
                tempY.append(l[1])
            else:
                xs.append(tempX)
                ys.append(tempY)
                tempX = []
                tempY = []

    return xs, ys

def update(i):
    global xs
    global ys
    if not xs[i] or not ys[i]:  # Check for empty lists
        return
    
    xbar = reduce((lambda a, b: a + b),
                  map(lambda x: float(x), xs[i])) / len(xs[i])
    ybar = reduce((lambda a, b: a + b),
                  map(lambda y: float(y), ys[i])) / len(ys[i])
    
    ax.clear()  # Clear previous frame
    ax.scatter(xs[i], ys[i], s=1)
    ax.scatter(xbar, ybar, color='blue')
    
    # Maintain consistent axes limits
    ax.set_xlim(0, 1)
    ax.set_yticklabels([])
    ax.set_xticklabels([])



if __name__ == '__main__':

    xs, ys = gather_data()

    fig, ax = plt.subplots()
    fig.set_tight_layout(True)
    # plt.axis('off')

    # plt.title('Genetic Evaluation')
    plt.xlabel('Weights')
    plt.ylabel('Tournament Scores')
    ax.set_yticklabels([])
    ax.set_xticklabels([])

    plt.xlim(0, 1)
    print ('Number of points:', len(ys))
    anim = FuncAnimation(fig, update, frames=np.arange(
        0, len(ys)), interval=20, repeat_delay=0)
    anim.save('linen.gif', dpi=120, writer='pillow')
    print('doneeroo: output file is linen.gif')
