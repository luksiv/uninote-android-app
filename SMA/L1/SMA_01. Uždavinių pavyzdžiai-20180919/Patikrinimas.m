function Patikrinimasxd
clc,close all
figure(1); grid on; hold on; axis equal;
title('1.4x^5 + 0.85x^4 - 8.22x^3 - 4.67x^2 + 6.51x + 0.86');
x=-7:.1:7;
plot(x,f(x), '-k');

%-------------------
ivery = [0, 0];
% Grubus ivertis
%iver = [-6.8714, 6.8714];
%scatter(iver, ivery, 'filled', '^', 'red');
%legend({'daugianaris f(x)', 'grubus saknu intervalo ivertis'}, 'Location', 'northeast');
%-------------------
% Tikslesnis ivertis
iver = [-6.8714, 3.4231];
scatter(iver, ivery, 'filled', '^', 'green');
legend({'daugianaris f(x)', 'tikslesnis saknu intervalo ivertis'}, 'Location', 'northeast');
%-------------------
%-------------------

% Saknu atskyrimas
%vr = [-2.0714, -1.1714, 0.028599999999997, 0.928599999999997, 2.4286];
%ar = [-2.3714, -1.4714, -0.271400000000003, 0.628599999999997, 2.1286];
%avry = [0, 0, 0, 0, 0];
%scatter(vr, ygr, 15, '<', 'filled', 'blue');
%scatter(ar, ygr, 15, '>', 'filled', 'red');
%legend({'daugianaris f(x)', 'virsutinis rezis', 'apatinis rezis'}, 'Location', 'northeast');
%-------------------
%-------------------

xlim([-8 8]);
ylim([-8 8]);
end

function F = f(x)
    F = 1.4 * x.^5 + 0.85 * x.^4 - 8.22 * x.^3 - 4.67 * x.^2 + 6.51 * x + 0.86;
end


    