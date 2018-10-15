
% Broideno metodas
function pagrindine
clc,close all

eps=1e-10
itmax=100000
x=[1;1;1;1];   
n=length(x);

% Pradines Jakobio matricos reiksmes apskaiciavimas:
dx=sum(abs(x))*1e-5;
f0=f(x);
for i=1:n, x1=x; x1(i)=x1(i)+dx; f1=f(x1); A(:,i)=(f1-f0)/dx; end
% A=-eye(n)*10   %*10  *(-10) 

% Broideno metodo iteracijos:
fi=f(x);  % pradine funkcijos reiksme
for iii=1:itmax
    deltax=-A\f(x); x=x+deltax; fi1=f(x); A=A+(fi1-fi-A*deltax)*deltax'/(deltax'*deltax);
    
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
function F=f(X) 
 F(1)=X(1)+2*X(2)+X(3)+4*X(4)-20.7;
 F(2)=X(1)^2+2*X(1)*X(2)+X(4)^3-15.88;
 F(3)=X(1)^3+X(3)^2+X(4)-21.218;
 F(4)=3*X(2)+X(3)*X(4)-7.9;
 F=F(:);
 return
end 



