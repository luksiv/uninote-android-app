
% Broideno metodas 2 lygciu sistemai su vaizdavimu
function pagrindine
clc,close all
scrsz = get(0,'ScreenSize')

x=[-5:0.2:5];y=[-6:0.2:6];
Z=pavirsius(@f,x,y);
% [left, bottom, width, height]
fig1=figure(1);set(fig1,'Position',[50 scrsz(4)/1.8 scrsz(3)/3 scrsz(4)/3],'Color','w');
hold on,grid on,axis equal,axis([min(x) max(x) min(y) max(y) 0 5]);view([0 0 1]);xlabel('x'),ylabel('y');
mesh(x,y,Z(:,:,1)','FaceAlpha',0.2,'FaceColor','r','EdgeColor','r');contour(x,y,Z(:,:,1)',[0 0],'LineWidth',1.5,'LineColor','r');
mesh(x,y,Z(:,:,2)','FaceAlpha',0.2,'FaceColor','b','EdgeColor','b');contour(x,y,Z(:,:,2)',[0 0],'LineWidth',1.5,'LineColor','b');
xx=axis; fill([xx(1),xx(1),xx(2),xx(2)],[xx(3),xx(4),xx(4),xx(3)],'b','FaceAlpha',0.2);
pause
xx=axis; fill([xx(1),xx(1),xx(2),xx(2)],[xx(3),xx(4),xx(4),xx(3)],'b','FaceAlpha',0.2);

eps=1e-5;itmax=100;
x=[3.5;-8];
n=length(x);

% Pradinio Jakobio matricos artinio apskaiciavimas: **********
if 1  % pradinis A artinys skaiciuojamas pagal skaitinio diferencijavimo formule;
    dx=sum(abs(x))*1e-5; f0=f(x);
    for i=1:n
        x1=x;x1(i)=x1(i)+dx; f1=f(x1); A(:,i)=(f1-f0)/dx;
    end
else,  % pradinis A artinys yra parinkta istrizainine matrica 
    A=-eye(n)*(-11);   %*10  *15 *8) 
end  

% Broideno metodo iteracijos:
ff=f(x);  % pradine funkcijos reiksme
figure(1);plot3(x(1),x(2),0,'b*');line([x(1),x(1)],[x(2),x(2)],[0,ff(1)],'Color','black');

for iii=1:itmax
    s=-A\ff; x1=x+s; ff1=f(x1); A=A+(ff1-ff-A*s)*s'/(s'*s);
    figure(1);plot3(x1(1),x1(2),0,'r*');line([x(1),x1(1)],[x(2),x1(2)],[0,0],'Color','red');
    line([x(1),x1(1)],[x(2),x1(2)],[ff(1),0*ff1(1)],'Color','magenta','LineWidth',2.5);
    pause;
    
    tikslumas=norm(s)/(norm(x)+norm(s));
    
    fprintf(1,'\n iteracija %d  tikslumas %g',iii,tikslumas);
    if tikslumas < eps,         fprintf(1,'\n sprendinys x =');fprintf(1,'  %g',x); plot3(x(1),x(2),0,'kp','MarkerSize',14); break;
    elseif iii == itmax,fprintf(1,'\n ****tikslumas nepasiektas. Paskutinis artinys x =');fprintf(1,'  %g',x); plot3(x(1),x(2),0,'gp'); break;
    end
    
    ff=ff1; x=x1;
end
fprintf(1,'\n funkcijos reiksme f =');  fprintf(1,'  %g',f(x));fprintf(1,'\n');
return
end

%   Lygciu sistemos funkcija 
    function fff=f(x)
          fff=[ sin(4*x(1))+x(2)^2-2;
                x(1)^2+x(2)^2-(0.5*sin(6*atan(x(2)/x(1)))+1.5)^2];
    return
    end

    function Z=pavirsius(funk,x,y)
        for i=1:length(x), for j=1:length(y)
              Z(i,j,1:2)=funk([x(i),y(j)]);
        end, end
    return
    end