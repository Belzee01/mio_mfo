set terminal png size 800,600
set o 'sarBentCiagar_fitness_iteration_60.png'
set xl 'Numer iteracji'
set yl 'Wartość minimum'
set log x
set log y
set title 'Bent Cigar agents D10'
plot 'sarBentCiagar_fitness_iteration_60.dat' u 1:2 w lines t '60 ciem',\
'sarBentCiagar_fitness_iteration_120.dat' u 1:2 w lines t '120 ciem',\
'sarBentCiagar_fitness_iteration_180.dat' u 1:2 w lines t '180 ciem'

set terminal png size 800,600
set o 'sarRastrigin_fitness_iteration_60.png'
set xl 'Numer iteracji'
set yl 'Wartość minimum'
set title 'Rastrigin agents D10'
plot 'sarRastrigin_fitness_iteration_60.dat' u 1:2 w lines t '60 ciem',\
'sarRastrigin_fitness_iteration_120.dat' u 1:2 w lines t '120 ciem',\
'sarRastrigin_fitness_iteration_180.dat' u 1:2 w lines t '180 ciem'

set terminal png size 800,600
set o 'sarRosenbrock_fitness_iteration_60.png'
set xl 'Numer iteracji'
set yl 'Wartość minimum'
set title 'Rosenbrock agents D10'
plot 'sarRosenbrock_fitness_iteration_60.dat' u 1:2 w lines t '60 ciem',\
'sarRosenbrock_fitness_iteration_120.dat' u 1:2 w lines t '120 ciem',\
'sarRosenbrock_fitness_iteration_180.dat' u 1:2 w lines t '180 ciem'

set terminal png size 800,600
set o 'sarZakharov_fitness_iteration_60.png'
set xl 'Numer iteracji'
set yl 'Wartość minimum'
set title 'Zakharov agents D10'
plot 'sarZakharov_fitness_iteration_60.dat' u 1:2 w lines t '60 ciem',\
'sarZakharov_fitness_iteration_120.dat' u 1:2 w lines t '120 ciem',\
'sarZakharov_fitness_iteration_180.dat' u 1:2 w lines t '180 ciem'