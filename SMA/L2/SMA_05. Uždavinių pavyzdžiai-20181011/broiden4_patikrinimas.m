
fun = @f;
x0=[ 4;4;4;4];
x = fsolve(fun, x0)

x0=[ 1;1;1;1];
x = fsolve(fun, x0)

f([4;4;4;4])

    
%   Lygciu sistemos funkcija 
function F=f(x) 
 F(1)=2*x(1) + 5*x(2) - 2*x(3) + x(4) - 17;
 F(2)=-1*x(2)^2 + 3*x(3)^2 - 18;
 F(3)=x(3)^3 + 4*x(1)*x(3) - 2*x(4)^2 - 79;
 F(4)=5*x(1) - 15*x(2) + x(3) + 4*x(4) + 25;
 F=F(:);
 return
end 