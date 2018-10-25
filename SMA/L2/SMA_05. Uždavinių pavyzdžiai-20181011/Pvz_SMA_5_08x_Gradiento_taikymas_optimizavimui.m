
% Greiciausio nusileidimo metodas

function pagrindine
clc,close all
scrsz = get(0,'ScreenSize')
        height=[2    3    2   2]
        alfax=[ 0.5 0.5 0.25 0.5];
        ax=   [  0   3   -2   -2];
        alfay=[ 0.5 0.5 0.5 0.25];
        ay=   [  0   -2   3  -3 ];
        
%         height=[ 3     2]
%         alfax=[ 0.5  0.5];
%         ax=   [  3   -2];
%         alfay=[ 0.5  0.25];
%         ay=   [  -2   -3 ];
%         
xx=[-5:0.1:5];yy=[-6:0.1:6]; % grid
[X,Y]=meshgrid(xx,yy); Z=kalnas(X,Y); % mesh and mountains function
fig1=figure(1);set(fig1,'Position',[50 scrsz(4)/1.8 scrsz(3)/3 scrsz(4)/3]); 
hold on,grid on,axis equal,axis([min(xx) max(xx) min(yy) max(yy) -0.1 max(1,max(height)*1.2)]);view([1 1 1]);xlabel('x'),ylabel('y');
surf(X,Y,Z,'FaceAlpha',0.8,'EdgeColor',0.8*[1 1 1],'FaceColor',0.9*[0.6 1 0],'FaceLighting','gouraud');light('Position',[5,5,5]);

npoints1=30;xp=5;yp=-5;xg=0;yg=2;xgf=-5;ygf=5;
npoints2=30
x01=[xp:(xg-xp)/npoints1:xg];
x02=[xg:(xgf-xg)/npoints2:xgf];
x0=[x01(1:end-1),x02]

y01=[yp:(yg-yp)/npoints1:yg];
y02=[yg:(ygf-yg)/npoints2:ygf];
y0=[y01(1:end-1),y02]

lng=min(length(x0),length(y0));
x0=x0(1:lng);y0=y0(1:lng);
z0=kalnas(x0,y0);  % intial trial solution
n=length(x0);  % number of points including the first and the last
hndl=plot3(x0,y0,z0,'r-*');
ABF=30;    % penalty coefficient due to altidude change 
pradinis_kelio_ilgis=target(x0,y0,1);
pradine_tikslo_funkcija=target(x0,y0,ABF);
title(sprintf('kelio ilgis= %g\ntikslo funkcija= %g,  ABF=%d',pradinis_kelio_ilgis,pradine_tikslo_funkcija,ABF))
pause

eps=1e-5; % precision for optimal solution
EPS=1e-1;
step0=2;step=step0; 
itmax=1000  % kiek kartu galima keisti krypti
x=x0;y=y0;  % initial approximation
hndl1=[];

for iii=1:itmax
    fff=target(x,y,ABF);                   
    [gradx,grady]=gradient(x,y,ABF); grad=[gradx,grady];
    for iiii=1:20  % ejimas pagal mazejimo krypti
        delta=grad/norm(grad)*step; % true step along gradient
        
        % gradient projection perpendicular to the path projection on xOy:
       nrm= [y0(3:end)-y0(1:end-2)
             x0(1:end-2)-x0(3:end)];  % normal to the path right 
       grd=[delta(1:n-2);
            delta(n-1:2*n-4)]
        pause
       for ii=1:n-2 
           grd(:,ii)=dot(nrm(:,ii),grd(:,ii))*nrm(:,ii)/norm(nrm(:,ii)); 
       end
       delta=[grd(1,:),grd(2,:)]; 
       
        x(2:n-1)=x(2:n-1)-delta(1:n-2); 
        y(2:n-1)=y(2:n-1)-delta(n-1:2*n-4);
        zz=kalnas(x,y);
  
            % vizualizavimas:
            if ~isempty(hndl1),delete(hndl1);end 
            hndl1=plot3(x,y,zz,'k*');
            fff1=target(x,y,ABF);
            kelio_ilgis=target(x,y,1);
        title(sprintf('kelio ilgis= %g  --> %g\n tikslo funkcija= %g --> %g,  ABF=%g',...
            pradinis_kelio_ilgis,kelio_ilgis,pradine_tikslo_funkcija,fff1,ABF));
        pause(0.01);
       
        tikslumas=norm(fff-fff1)/(norm(fff)+norm(fff1));
        fprintf(1,'\n kryptis %d  tikslumas %g  zingsnis % g',iii,tikslumas,step);
        if tikslumas < eps,break,end
        if fff1 > fff
            x(2:end-1)=x(2:end-1)+delta(1:n-2); 
            y(2:end-1)=y(2:end-1)+delta(n-1:2*n-4); 
            step=step/2; 
        else, fff=fff1; if tikslumas < EPS, step=step*2;end
        end
    end
%     step=step0;
        % smoothing the path 
%         x(2:end-1)=(x(3:end)+x(1:end-2))/2;
%         y(2:end-1)=(y(3:end)+y(1:end-2))/2;
        
        
end
    return

%   Mountains function 
    function fff=kalnas(x,y)
        fff=0;for jj=1:length(alfax), fff=fff+height(jj)./(1+alfax(jj)*(x-ax(jj)).^2+alfay(jj).*(y-ay(jj)).^2); end
    return
    end
    
%   Target function
    function rez=target(x,y,AB)
        z=AB*kalnas(x,y);
        rez=sum(sqrt((x(2:end)-x(1:end-1)).^2+(y(2:end)-y(1:end-1)).^2+(z(2:end)-z(1:end-1)).^2));
    return
    end
 
%   Derivatives of mountain function
    function [dfffx,dfffy]=Dkalnas(x,y)
        dfffx=0;dfffy=0;
        for j=1:length(alfax), 
            dfffx=dfffx-height(j)*(1+alfax(j)*(x-ax(j)).^2+alfay(j).*(y-ay(j)).^2).^(-2).*2*alfax(j).*(x-ax(j));
            dfffy=dfffy-height(j)*(1+alfax(j)*(x-ax(j)).^2+alfay(j).*(y-ay(j)).^2).^(-2).*2*alfay(j).*(y-ay(j));
        end
    return
    end
 
    % Gradient
    function [rezx,rezy]=gradient(x,y,AB)
        % returns 2 vectors of length n-2
        z=kalnas(x,y);
        [dfffx1,dfffy1]=Dkalnas(x,y);
        dfffx=dfffx1(2:end-1);
        dfffy=dfffy1(2:end-1);
        rezx=2*(x(2:end-1)-x(1:end-2))-2*(x(3:end)-x(2:end-1))+2*AB*(z(2:end-1)-z(1:end-2)).*dfffx-2*AB*(z(3:end)-z(2:end-1)).*dfffx;
        rezy=2*(y(2:end-1)-y(1:end-2))-2*(y(3:end)-y(2:end-1))+2*AB*(z(2:end-1)-z(1:end-2)).*dfffy-2*AB*(z(3:end)-z(2:end-1)).*dfffy;
    return
    end
end
  