set terminal png size 800,600
set o 'BentCigarD2.png'
set xl 'os X'
set yl 'os Y'
set title 'Bent Cigar D2'
set pm3d
splot 'bentcigar.dat' u 2:1:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'ZakharovD2.png'
set xl 'os X'
set yl 'os Y'
set title 'Zakharov D2'
set pm3d
splot 'zakharov.dat' u 2:1:3 w pm3d lt -1 t ''


set terminal png size 800,600
set o 'RastriginD2.png'
set xl 'os X'
set yl 'os Y'
set title 'Rastrigin D2'
set pm3d
splot 'rastrigin.dat' u 2:1:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'RosenbrockD2.png'
set xl 'os X'
set yl 'os Y'
set title 'Rosenbrock D2'
set pm3d
splot 'rosenbrock.dat' u 2:1:3 w pm3d lt -1 t ''