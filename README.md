![Genetic Algorithm running for a basic prisoner's dilemma](gifs/small_long_always_defect.gif)

_GeneticOneMove strategy: Genetic algorithm running for a basic Prisoner's Dilemma. This shows the initial population narrowing to their optimum of always defecting, as well as the tournament scores of the population decreasing. This illustrates the reason for the name "Prisoner's Dilemma" - the Nash Equilibrium of the system is to always defect, and therefore score less points than if the system were to cooperate._

![Genetic Algorithm running on more difficult prisoner's dilema](gifs/small_evolution_ttt.gif)

_GeneticMemory strategy: Genetic algorithm running for a more challenging Prisoner's Dilemma. Each strategy is evolving the probability that it will cooperate or defect, depending on it's opponent's previous move. It can be seen here that a tit-for-tat strategy evolves to be most successful; i.e. each strategy copies it's opponent's previous move. This is a friendly strategy that punishes opponents when they defect, yet forgives them when they cooperate._

![GeneticMemory strategy](gifs/small_mem_to_defect_longer.gif)

_* interestingly enough, when the inital population's maximum initial weight is ~0.54 with a population size of 280, the population tends to a defecting strategy._

## Why

I was listening to [Dr. Robert Sapolsky's lecture on Human Behavioral Biology,](https://www.youtube.com/watch?v=NNnIGh9g6fA) where he discussed game theory and how certain game theory strategies were found in nature. 
It would be interesting to see if these strategies (e.g. [Tit-for-Tat](https://en.wikipedia.org/wiki/Tit_for_tat)) could be developed by a genetic algorithm.

## Genetic Twist

From [Dr. Robert Sapolsky's lecture above,](https://www.youtube.com/watch?v=NNnIGh9g6fA) he mentioned that Prisoner Dilemma Strategies for games iterated over time were found in nature. Further, Dr. Robert Axelrod and Dr. William D. Hamilton created the [Evolution of Cooperation](http://www-personal.umich.edu/~axe/research/Axelrod%20and%20Hamilton%20EC%201981.pdf) in 1981 which compared many different basic strategies together.

The most successful strategy from these trials was Tit-For-Tat. This strategy starts with cooperating, and then continues with copying the strategy of it's opponent in the previous round.

The goal of this project will be to create a genetic algorithm to play iterated rounds of the Prisoners Dilemma. Perhaps the genetic algorithm ends up modelling a Tit-For-Tat strategy?

## Implementation of Genetic Strategy 

#### Constant Decision Strategy

_strategy does not look at the opponents moves_

The tricky thing with implementing a genetic strategy here is the lack of continuity of the output of the strategies; i.e., when each strategy makes a move, it is either cooperate or defect.

The implementation that I chose was to assign the genetic strategies a `weight` between 0 and 1. When it is time to make a move, the strategy generates a random number between 0 and 1. If the number is less than the strategy's weight, the strategy cooperates. Else, it defects. See this [here, in the method `makeMove()`](https://github.com/Axel-Jacobsen/GeneticPrisonersDilemma/blob/master/src/main/java/GameTheory/Strategies/GeneticOneMove.java).

This gives a natural 'genetic' side of the genetic algorithm. By modifying the weight, the strategy tends towards defecting or cooperating (the results below shows that lower weights which tend to defect perform better than higher weights).

#### One Move Strategy

_strategy looks at the opponents previous move_

Similar to the Constant Decision Strategy, the tricky thing is getting some sort of continuity of the output.

Also similar to the Constant Decision Strategy, this strategy has a `weight` between 0 and 1. When it is time to make a move, the strategy generates a random number between 0 and 1. The strategy "ands" the previous move of the opponent and weather the random number is less than the weight. That is, the strategy returns `opponent_previous_move && random_number < weight`. If this is true, the strategy cooperates, and if it is false, the strategy defects. See this [here, in the method `makeMove()`](https://github.com/Axel-Jacobsen/GeneticPrisonersDilemma/blob/master/src/main/java/GameTheory/Strategies/GeneticMemory.java).

When `weight` is close to 1, the strategy copies the opponents previous move. when `weight` is near zero, the strategy returns false, or it tends to defect.
