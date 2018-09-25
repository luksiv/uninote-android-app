function Patikrinimasxd
clc,close all

pol = @f;

x1 = -2.2214;
x2 = -1.3214;
x3 = -0.1214;
x4 = 0.7786;
x5 = 2.2786;

r = roots([1.4 0.85 -8.22 -4.67 6.51 0.86]);
sr = sort(r);

fprintf("%16s\n", "fzero poly");
fprintf("%16.11f\n", fzero(pol, x1));
fprintf("%16.11f\n", fzero(pol, x2));
fprintf("%16.11f\n", fzero(pol, x3));
fprintf("%16.11f\n", fzero(pol, x4));
fprintf("%16.11f\n\n", fzero(pol, x5));

fprintf("%16s\n", "roots poly");
fprintf("%16.11f\n", sr(1));
fprintf("%16.11f\n", sr(2));
fprintf("%16.11f\n", sr(3));
fprintf("%16.11f\n", sr(4));
fprintf("%16.11f\n\n", sr(5));

fprintf("%16s %16s\n", "fzero poly", "roots poly");
fprintf("%16.11f %16.11f\n", fzero(pol, x1), sr(1));
fprintf("%16.11f %16.11f\n", fzero(pol, x2), sr(2));
fprintf("%16.11f %16.11f\n", fzero(pol, x3), sr(3));
fprintf("%16.11f %16.11f\n", fzero(pol, x4), sr(4));
fprintf("%16.11f %16.11f\n", fzero(pol, x5), sr(5));

tra = @v;
vr = [-0.5, 1, 6];
ar = [-1, 0.5, 5.5];
artin = [-5.55, -4.05, -2.25, -0.75, 0.75, 2.25, 4.05, 5.55];
fprintf("\n%16.11s\n", "fzero trancend");
for i =1:8
    fprintf("%16.11f\n",fzero(tra, artin(i))); 
end
fprintf("\n%16.11s\n", "artiniai");
for i = 1:8
    fprintf("%16.3f\n",artin(i));
end

fprintf("\n%16.11s\n", "intervalai");
for i = 1:8
    fprintf("[%.3f; %.3f]\n",ar(i), vr(i));
end

end

function F = f(x)
    F = 1.4 * x.^5 + 0.85 * x.^4 - 8.22 * x.^3 - 4.67 * x.^2 + 6.51 * x + 0.86;
end

function G = g(x)
    G = cos(2*x)*exp(1).^(-1*((x/2).^2));
end

function v = v(x)
    v = pi .* x.^2 .*(6-x)-6
end