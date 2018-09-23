function Patikrinimasxd
clc,close all
figure(1); grid on; hold on; axis equal;
title("F(x) šaknys");
p = [1.4, 0.85, -8.22, -4.67, 6.51, 0.86];
r = roots(p);
x=-4:.1:4;
plot(x,f(x), '-b');
scatter(real(r),imag(r),'filled','red')
legend({'y = F(x)','šaknys'},'Location','northeast')
xlim([-4 4]);
ylim([-4 5]);
disp(by(0));
end

function F = f(x)
    F = 1.4 * x.^5 + 0.85 * x.^4 - 8.22 * x.^3 - 4.67 * x.^2 + 6.51 * x + 0.86;
end

function boldX = bx(x)
    boldX = x*0;
end

function boldY = by(y)
    boldY = y*0.000000001;
end
    