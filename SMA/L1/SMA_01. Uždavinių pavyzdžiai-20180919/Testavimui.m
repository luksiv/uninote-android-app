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

end

function F = f(x)
    F = 1.4 * x.^5 + 0.85 * x.^4 - 8.22 * x.^3 - 4.67 * x.^2 + 6.51 * x + 0.86;
end