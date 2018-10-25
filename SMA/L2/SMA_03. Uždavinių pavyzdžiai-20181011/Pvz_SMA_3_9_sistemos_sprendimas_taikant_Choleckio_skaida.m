
% Choleckio skaidos algoritmo taikymas lygciu sistemos sprendimui

clc,clear all
disp("A pradinis");
% A=[ 1 -1  0  0;
%    -1  2 -1  0;
%    0  -1  2 -1;
%    0   0 -1  2]
% b=[2;0;0;0]

A=[64 16 32 8;
   16 8  0  0;
   32 0 48  0;
   8  0  0 10]
b =[256;
    48;
    112;
    96];
n=size(A,1);
Aprad=A;
% Choleckio L'*L skaida
disp("Pradzia L'*L skaidos");
disp("------------");
for e=1:n    
    disp(e + " ciklas");
    A
    disp("~~");
    disp(A(e,e));
    disp(sum(A(1:e-1,e).^2));
    disp(A(e,e)-sum(A(1:e-1,e).^2));
    disp("~~");
    % A(e,e) - virs jos esanciu skaiciu kvadratu suma
    A(e,e)=sqrt(A(e,e)-sum(A(1:e-1,e).^2));    
    for s=e+1:n   
        A(1:e-1,e)
        A(1:e-1,s)
        A(e,s)=(A(e,s)-A(1:e-1,e)'*A(1:e-1,s))/A(e,e);
    end
    A
    disp("------------");
end
disp("Pabaiga L'*L skaidos");
A
% 1-as atvirkstinis zingsnis, sprendziama  L'y=b, y->b
disp("1-as atvirkstinis zingsnis, sprendziama  L'y=b, y->b");
for i=1:n
    disp(i + " ciklas")
    disp("kas yra b(i,:)");
    A
    b
    A(1:i-1,i)'
    b(1:i-1)
    A(1:i-1,i)'*b(1:i-1)
    disp("~_-~--~-~_~_");
    b(i,:)=(b(i,:)-A(1:i-1,i)'*b(1:i-1))/A(i,i);
end
disp("1-as atvirkstinis zingsnis pabaiga");
b

% 2-as atvirkstinis zingsnis   Ux=b,  x->b
for i=n:-1:1
    disp("kas yra b(i,:)");
    A
    A(i,i+1:n)
    b
    b(i+1:n)
    A(i,i+1:n)*b(i+1:n)
    disp("~_-~--~-~_~_");
    b(i)=(b(i)-A(i,i+1:n)*b(i+1:n))/A(i,i);
end
disp("2-as atvirkstinis zingsnis   Ux=b,  x->b");
b

Aprad*b