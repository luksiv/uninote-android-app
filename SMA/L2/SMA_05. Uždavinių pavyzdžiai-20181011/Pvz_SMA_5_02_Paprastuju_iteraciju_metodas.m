
%Paprastuju iteraciju metodas lygciu sistemai

function pagrindine
clc,close all
scrsz = get(0,'ScreenSize')

x=[-5:0.5:5];y=[-6:0.5:6];
Z=pavirsius(@f,x,y);
% [left, bottom, width, height]
fig1=figure(1);set(fig1,'Position',[50 scrsz(4)/1.8 scrsz(3)/3 scrsz(4)/3]);
hold on,grid on,axis equal,axis([min(x) max(x) min(y) max(y) 0 5]);view([1 1 1]);xlabel('x'),ylabel('y');
mesh(x,y,Z(:,:,1)','FaceAlpha',0.2);contour(x,y,Z(:,:,1)',[0 0],'LineWidth',1.5);
xx=axis;
fill([xx(1),xx(1),xx(2),xx(2)],[xx(3),xx(4),xx(4),xx(3)],'c','FaceAlpha',0.2);


fig2=figure(2),set(fig2,'Position',[scrsz(3)/3 scrsz(4)/1.8 scrsz(3)/3 scrsz(4)/3]);
hold on,grid on,axis equal,axis([min(x) max(x) min(y) max(y) 0 max(x)]);view([1 1 1]);xlabel('x'),ylabel('y')
mesh(x,y,Z(:,:,2)','FaceAlpha',0.2);contour(x,y,Z(:,:,2)',[0 0],'LineWidth',1.5)
xx=axis;
fill([xx(1),xx(1),xx(2),xx(2)],[xx(3),xx(4),xx(4),xx(3)],'b','FaceAlpha',0.2);
xlabel('x'),ylabel('y')

eps=1e-2;itmax=100;alpha=[1;1]*10;
x=[-3;-1.2];
ff=f(x);
figure(1);plot3(x(1),x(2),0,'b*');%plot3(x(1),x(2),ff(1),'k*');line([x(1),x(1)],[x(2),x(2)],[0,ff(1)]);
figure(2);plot3(x(1),x(2),0,'b*');%plot3(x(1),x(2),ff(2),'k*');line([x(1),x(1)],[x(2),x(2)],[0,ff(2)]);

for iii=1:itmax
    ff=f(x);
    x1=x+f(x)./alpha;
figure(1);plot3(x1(1),x1(2),0,'r*');line([x(1),x1(1)],[x(2),x1(2)],[0,0],'Color','red');
%plot3(x(1),x(2),ff(1),'k*');line([x(1),x(1)],[x(2),x(2)],[0,ff(1)]);
figure(2);plot3(x1(1),x1(2),0,'r*');line([x(1),x1(1)],[x(2),x1(2)],[0,0],'Color','red');
%plot3(x(1),x(2),ff(2),'k*');line([x(1),x(1)],[x(2),x(2)],[0,ff(2)]);
    pause;
    tikslumas=norm(ff);
    
    fprintf(1,'\n iteracija %d  tikslumas %g',iii,tikslumas);
    if tikslumas < eps
        fprintf(1,'\n sprendinys x =');
        fprintf(1,'  %g',x);
        break
    elseif iii == itmax
        fprintf(1,'\n ****tikslumas nepasiektas. Paskutinis artinys x =');
        fprintf(1,'  %g',x);
        break
    end
    x=x1;

end




return







fig3=figure(3),set(fig3,'Position',[scrsz(3)/5 scrsz(4)/5 scrsz(3)/3 scrsz(4)/3]);
figure(3),hold on,grid on,axis equal
contour(x,y,Z(:,:,1)',[0 0],'LineWidth',1.5)
contour(x,y,Z(:,:,2)',[0 0],'LineWidth',1.5)
% contour(x,y,Z(:,:,1)')
% contour(x,y,Z(:,:,2)')
xlabel('x'),ylabel('y')

return
end

%   Lygciu sistemos funkcija 
    function fff=f(x)
    fff=[x(1)^2+x(2)^2-2;
         x(1)^2-x(2)^2];
    return
    end
    
%  Jakobio matrica
    function dfff=df(x)
        dfff=[2*x(1), 2*x(2);
              2*x(1), -2*x(2)];
    return
    end
    
    function Z=pavirsius(funk,x,y)
    for i=1:length(x)
        for j=1:length(y)
            Z(i,j,1:2)=funk([x(i),y(j)]);
        end
    end
        
    return
    end