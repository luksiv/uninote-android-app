
% Niutono metodas su simboliniu diferencijavimu

function pagrindine
clc,close all
syms x1 x2 x3 x4
 X=[x1; x2; x3; x4];
 F(1)=X(1)+2*X(2)+X(3)+4*X(4)-20.7;
 F(2)=X(1)^2+2*X(1)*X(2)+X(4)^3-15.88;
 F(3)=X(1)^3+X(3)^2+X(4)-21.218;
 F(4)=3*X(2)+X(3)*X(4)-7.9;
 
 F=F(:)

 DF=jacobian(F,X)

eps=1e-10
itmax=100
x=[1;1;1;1];
% x=[-0.828147  6.95332  -4.33552  2.98925]'


for iii=1:itmax
    deltax=-eval(subs(DF,X,x))\eval(subs(F,X,x));
    x=x+deltax;
    tikslumas=norm(deltax)/(norm(x)+norm(deltax));
    
    fprintf(1,'\n iteracija %d  tikslumas %g',iii,tikslumas);
    if tikslumas < eps
        fprintf(1,'\n sprendinys x =');    fprintf(1,'  %g',x);    fprintf(1,'\n funkcijos reiksmes f =');
        fprintf(1,'  %g',eval(subs(F,X,x)));
        break
    elseif iii == itmax
        fprintf(1,'\n ****tikslumas nepasiektas. Paskutinis artinys x =');    fprintf(1,'  %g',x);
        fprintf(1,'\n funkcijos reiksmes f =');    fprintf(1,'  %g',eval(subs(F,X,x)));
        break
    end
end

fprintf(1,'\n');

    return
end


