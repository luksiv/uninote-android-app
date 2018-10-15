
%Grafinis metodas lygciu sistemai

function pagrindine
clc,close all

x=[-10:0.1:10];y=[-6:0.1:6];
Z=pavirsius(@f,x,y);
figure(1),hold on,grid on,axis equal,axis([min(x) max(x) min(y) max(y) -10 50]);view([1 1 1]);
xlabel('x'),ylabel('y');
mesh(x,y,Z(:,:,1)','FaceAlpha',0.2);contour(x,y,Z(:,:,1)',[0,0 ],'LineWidth',1.5);
xx=axis;
fill([xx(1),xx(1),xx(2),xx(2)],[xx(3),xx(4),xx(4),xx(3)],'c','FaceAlpha',0.2);

figure(2),hold on,grid on,axis equal,axis([min(x) max(x) min(y) max(y) 0 5]);view([1 1 1]);
xlabel('x'),ylabel('y')
mesh(x,y,Z(:,:,2)','FaceAlpha',0.2);contour(x,y,Z(:,:,2)',[0 0],'LineWidth',1.5)
xx=axis;
fill([xx(1),xx(1),xx(2),xx(2)],[xx(3),xx(4),xx(4),xx(3)],'b','FaceAlpha',0.2);




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
    fff=[(x(1)^2)/(((x(2) + cos(x(1)))^2)+1) - 2;
         ((x(1)/3)^2) + ((x(2) + cos(x(1)))^2) - 5];
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