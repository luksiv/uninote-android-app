
% Broideno metodas
function pagrindine
clc,close all

eps=1e-10
itmax=100000
x=[ 4;4;4;4];   
n=length(x);

% Pradines Jakobio matricos reiksmes apskaiciavimas:
dx=sum(abs(x))*1e-5;
f0=f(x);
for i=1:n 
    x1=x; 
    x1(i)=x1(i)+dx; 
    f1=f(x1); 
    A(:,i)=(f1-f0)/dx; 
end
A
% A=-eye(n)*10   %*10  *(-10) 

% Broideno metodo iteracijos:
fi=f(x);  % pradine funkcijos reiksme
for iii=1:itmax
    deltax=-A\f(x); 
    x=x+deltax; 
    fi1=f(x); 
    A=A+(fi1-fi-A*deltax)*deltax'/(deltax'*deltax);
    
    tikslumas=norm(deltax)/(norm(x)+norm(deltax));
    fprintf(1,'\n iteracija %d  tikslumas %g',iii,tikslumas);
    if tikslumas < eps
        fprintf(1,'\n sprendinys x ='); fprintf(1,'  %g',x);
        fprintf(1,'\n funkcijos reiksme f ='); fprintf(1,'  %g',f(x));
        break
    elseif iii == itmax
        fprintf(1,'\n ****tikslumas nepasiektas. Paskutinis artinys x ='); fprintf(1,'  %g',x);
        fprintf(1,'\n funkcijos reiksme f ='); fprintf(1,'  %g',f(x));
        break
    end
fi=fi1;
end

    return
end

%   Lygciu sistemos funkcija 
function F=f(x) 
 F(1)=2*x(1) + 5*x(2) - 2*x(3) + x(4) - 17;
 F(2)=-1*x(2)^2 + 3*x(3)^2 - 18;
 F(3)=x(3)^3 + 4*x(1)*x(3) - 2*x(4)^2 - 79;
 F(4)=5*x(1) - 15*x(2) + x(3) + 4*x(4) + 25;
 F=F(:);
 return
end 



