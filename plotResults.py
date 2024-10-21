"""
Python file to plot the results of tournaments; Python 2 or 3
"""

import matplotlib.pyplot as plt

def gather_data():
        xs = []
        ys = []

        with open('geneticRes.txt', 'r') as fromFile:
            for line in fromFile:
                l = line.strip().split()
                if len(l) >= 2:
                    xs.append(l[0])
                    ys.append(l[1])
                else:
                    print(f"Skipping invalid line: {line}")

        fig, ax = plt.subplots()
        ax.scatter(xs, ys)
        fig.set_tight_layout(True)

        plt.xlabel('Weights')
        plt.ylabel('Tournament Scores')

        plt.xlim(-0.1, 1.1)

        plt.show()


if __name__ == '__main__':
    gather_data()
