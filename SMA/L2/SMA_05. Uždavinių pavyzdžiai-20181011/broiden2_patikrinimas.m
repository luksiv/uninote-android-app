
%x0=[ 4; 4];
%x0=[ 4;-4];
%x0=[-4; 4];
x0=[-4;-4];
fun = @f;
x = fsolve(fun, x0)

function fff=f(x)
    fff=[((x(1)^2)/(((x(2) + cos(x(1)))^2)+1)) - 2;
         ((x(1)/3)^2) + ((x(2) + cos(x(1)))^2) - 5];
    return
    end