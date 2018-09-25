function Patikrinimasxd
clc,close all
figure(1); grid on; hold on; axis equal;
%title('1.4x^5 + 0.85x^4 - 8.22x^3 - 4.67x^2 + 6.51x + 0.86');
%title('cos(2x)e^{-(^{x}/_{2})^2}');
title('\pi h^2(6-h)-6');
x=-7:.1:7;
plot(x,v(x), '-k');

%-------------------
%ivery = [0, 0];
% Grubus ivertis
%iver = [-6.8714, 6.8714];
%scatter(iver, ivery, 'filled', '^', 'red');
%legend({'daugianaris f(x)', 'grubus saknu intervalo ivertis'}, 'Location', 'northeast');
%-------------------
% Tikslesnis ivertis
%iver = [-6.8714, 3.4231];
%scatter(iver, ivery, 'filled', '^', 'green');
%legend({'daugianaris f(x)', 'tikslesnis saknu intervalo ivertis'}, 'Location', 'northeast');
%-------------------
%-------------------

% Saknu atskyrimas
%vr = [-2.0714, -1.1714, 0.028599999999997, 0.928599999999997, 2.4286];
%ar = [-2.3714, -1.4714, -0.271400000000003, 0.628599999999997, 2.1286];
%avry = [0, 0, 0, 0, 0];
%legend({'daugianaris f(x)', 'virsutinis rezis', 'apatinis rezis'}, 'Location', 'northeast');
%vr = [-5.4, -3.9, -2.1, -0.600000000000002, 0.899999999999998, 2.4, 4.2, 5.7];
%ar = [-5.7, -4.2, -2.4, -0.900000000000002, 0.599999999999998, 2.1, 3.9, 5.4];
avry = [0, 0];
vr = [0.59439990604, 5.94598001144];
ar = [-1, 0.5, 5.5];
scatter(vr, avry, 30, 'red');
%scatter(ar, avry, 15, '>', 'filled', 'red');
%legend({'daugianaris v(h)', 'virsutinis rezis', 'apatinis rezis'}, 'Location', 'northeast');
legend({'daugianaris v(h)', 'v(h) = 0'}, 'Location', 'northeast');
%-------------------
%-------------------

xlim([-7 7]);
ylim([-2 2]);
end

function F = f(x)
    F = 1.4 * x.^5 + 0.85 * x.^4 - 8.22 * x.^3 - 4.67 * x.^2 + 6.51 * x + 0.86;
end

function G = g(x)
    G = cos(2 .* x) .* (exp(1).^(-1 .* ((x/2).^2)));
end

function v = v(x)
    v = pi .* x.^2 .*(6-x)-6
end


    