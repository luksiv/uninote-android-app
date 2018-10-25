using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MathNet.Numerics.LinearAlgebra;
using System.Windows.Forms.DataVisualization.Charting;

namespace SystemsOfLinearEquations
{
    public partial class Form1 : Form
    { 

        public Form1()
        {
            InitializeComponent();
        }
        /// <summary>
        /// Mygtukas "Baigti"
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
        public void ClearForm()
        {
            richTextBox1.Clear(); // isvalomas richTextBox1
            chart1.Series.Clear();
            rtb_opti.Clear();
        }
        private void op(Object x)
        {
            if (richTextBox1.Visible)
                richTextBox1.AppendText(x.ToString() + '\n');
            else
                rtb_opti.AppendText(x.ToString() + '\n');
        }
        private void sw(int x)
        {
            if(x == 1)
            {
                richTextBox1.Visible = true;
                rtb_opti.Visible = false;
                chart1.Visible = false;
            } else
            {
                richTextBox1.Visible = false;
                rtb_opti.Visible = true;
                chart1.Visible = true;
            }
        }
              

        // choleskio metodas
        private void btn_choleskio_Click(object sender, EventArgs e)
        {
            sw(1);
            double[,] CHA = { { 64, 16, 32, 8 }, { 16, 8, 0, 0 }, { 32, 0, 48, 0 }, { 8, 0, 0, 10 } }; // koeficientu matrica
            double[] CHB = { 256, 48, 112, 96 };// koeficientu vektorius 
            ClearForm();
            // ----------------------Pradiniai duomenys--------------------------
            // matricos generuojamos pagal virsuje aprasytus masyvus
            Matrix<double> A = Matrix<double>.Build.DenseOfArray(CHA);
            Vector<double> b = Vector<double>.Build.DenseOfArray(CHB);

            op("Pradiniai duomenys:");
            op("A =");
            op(A.ToString());
            op("b =");
            op(b.ToString());

            int n = A.Rank();

            // Choleckio L'*L skaida
            op("Choleckio L'*L skaida");
            for (int eil = 0; eil < n; eil++)
            {
                op("Ciklas nr. " + (eil+1));
                double suma = 0;
                if (eil != 0)
                {
                    Matrix<double> a = A.SubMatrix(0, eil, eil, 1);
                    for (int i = 0; i < a.Column(0).Count; i++)
                    {
                        suma += Math.Pow(a.Column(0)[i], 2);
                    }                    
                }
                // A(e,e) - virs jos esanciu skaiciu kvadratu suma
                A[eil, eil] = Math.Sqrt(Math.Abs(A[eil, eil] - suma));
                for (int stu = eil+1; stu < n; stu++)
                {
                    if(eil != 0)
                        A[eil, stu] = (A[eil, stu] - (A.SubMatrix(0, eil, eil, 1).Transpose() * A.SubMatrix(0, eil, stu, 1))[0, 0]) / A[eil, eil];
                    else
                        A[eil, stu] = (A[eil, stu]) / A[eil, eil];
                }
                op(A);
            }

            // 1-as atvirkstinis zingsnis, sprendziama  L'y=b, y->b
            op("1-as atvirkstinis zingsnis, sprendziama  L'y=b, y->b");
            for (int eil = 0; eil < n; eil++)
            {
                op("Ciklas nr. " + (eil + 1));
                if (eil != 0) {
                    b[eil] = (b[eil] - (A.SubMatrix(0, eil, eil, 1).Transpose() * b.SubVector(0, eil)).Sum()) / A[eil, eil];
                }
                else
                    b[eil] = (b[eil]) / A[eil, eil];
                op(b);
            }

            // 2-as atvirkstinis zingsnis   Ux=b,  x->b
            op("2-as atvirkstinis zingsnis   Ux=b,  x->b");
            for (int eil = n-1; eil >= 0; eil--)
            {
                op("Ciklas nr. " + (n-eil));
                if (eil != n-1)
                {
                    op(A.SubMatrix(eil, 1, eil + 1, n - eil - 1));
                    op(b.SubVector(eil+1, n-eil-1));
                    b[eil] = (b[eil] - (A.SubMatrix(eil, 1, eil+1, n - eil - 1) * b.SubVector(eil + 1, n - eil - 1)).Sum()) / A[eil, eil];
                }
                else
                    b[eil] = (b[eil]) / A[eil, eil];
                op(b);
            }
            op("Gauti rezultatai: ");
            op(A);
            op(b);
            op("Patikrinimas");
            Matrix<double> C = Matrix<double>.Build.DenseOfArray(CHA);
            op(C.Append(Vector<double>.Build.DenseOfArray(CHB).ToColumnMatrix()));
            for (int i = 0; i < n; i++)
            {
                op("               " + (i + 1) + " salyga");
                String output  = "";
                String output2 = "";
                double suma = 0;
                for (int o = 0; o < n; o++)
                {
                    suma += C[i, o] * b[o];
                    if(C[i,o] < 10)
                    {
                        output += " " + C[i, o] + " * x" + (o + 1);
                    }
                    else
                    {
                        output += C[i, o] + " * x" + (o + 1);
                    }

                    if (C[i, o] < 10)
                    {
                        if(b[o] < 10)
                            output2 += " " + C[i, o] + " * " + b[o];
                        else
                            output2 += " " + C[i, o] + " *" + b[o];
                    }
                    else
                    {
                        if (b[o] < 10)
                            output2 += C[i, o] + " * " + b[o];
                        else
                            output2 += C[i, o] + " *" + b[o];
                    }

                    if (o != n - 1)
                    {
                        output += " + ";
                        output2 += " + ";
                    } else
                    {
                        output += " = ";
                        output2 += " = ";
                    }


                }
                output2 += suma + "\n";
                op(output + output2);                
            }




        }
        // broideno metodas su 2 lygtim
        private Vector<double> f_bro2(Vector<double> x)
        {
            double f1 = ((Math.Pow(x[0], 2)) / ((Math.Pow((x[1] + Math.Cos(x[0])), 2)) + 1)) - 2;
            double f2 = (Math.Pow((x[0] / 3), 2)) + (Math.Pow((x[1] + Math.Cos(x[0])), 2)) - 5;
            double[] f12 = { f1, f2 };
            return Vector<double>.Build.DenseOfArray(f12);
        }
        private void btn_bro2_Click(object sender, EventArgs e)       
        {
            sw(1);
            ClearForm();
            double[] prArt = { double.Parse(tb_br2x1.Text), double.Parse(tb_br2x2.Text) };// pradiniai artiniai
            double tiksSiek = 1e-9;
            int iterMax = 100;
            op("Siekiamas tikslumas: " + tiksSiek);

            Vector<double> x = Vector<Double>.Build.DenseOfArray(prArt);
            int n = x.Count;
            op("Pradinis artinys:\n" + x);

            // Pradinio Jakobio matricos artinio apskaiciavimas
            // pradinis A artinys skaiciuojamas pagal skaitinio diferencijavimo formule
            double dx = 0;
            foreach (double item in x)
            {
                dx += Math.Abs(item);
            }
            dx *= 1e-5;
            Vector<double> f0 = f_bro2(x);
            Matrix<double> A = Matrix<double>.Build.Dense(n, n);
            for (int i = 0; i < n; i++)
            {
                Vector<double> x1 = Vector<double>.Build.DenseOfArray(prArt);
                x1[i] = x1[i] + dx;
                Vector<double> f1 = f_bro2(x1);
                Vector<double> f = ((f1 - f0) / dx);
                for (int o = 0; o < n; o++)
                {
                    A[o, i] = f[o];
                }
            }
            op("Pradinio Jakobio matricos artinys:\n" + A);

            // Broideno metodo iteracijos
            Vector<double> ff = f_bro2(x); // pradine funkcijos reikme

            for (int iii = 0; iii < iterMax; iii++)
            {
                Vector<double> deltax = (A * -1).Inverse() * ff;
                Vector<double> x1 = x + deltax;
                Vector<double> ff1 = f_bro2(x1);
                A =  A + ((ff1.ToColumnMatrix()-ff.ToColumnMatrix()- A * deltax.ToColumnMatrix())*deltax.ToRowMatrix()) / (deltax.ToRowMatrix() * deltax.ToColumnMatrix())[0,0];
                double tikslumas = deltax.Norm(2) / (x.Norm(2) + deltax.Norm(2));
                op("iteracija " + (iii + 1) + " tikslumas " + Math.Round(tikslumas, 11));
                if(tikslumas < tiksSiek)
                {
                    op("Sprendinys x = [" + x[0] + ", " + x[1] + "]");
                    op("Tikslumas = " + tikslumas);
                    break;
                } else if(iii == iterMax - 1)
                {
                    op("Tikslumas nepasiektas. Paskutinis artinys x = " + x);
                    break;
                }
                ff = ff1;
                x = x1;
            }

            





        }
        // broideno metodas su 4 lygtim
        private Vector<double> f_bro4(Vector<double> x)
        {
            double f1 = 2 * x[0] + 5 * x[1] - 2 * x[2] + x[3] - 17;
            double f2 = -1 * Math.Pow(x[1], 2) + 3 * Math.Pow(x[2], 2) - 18;
            double f3 = Math.Pow(x[2], 3) + 4 * x[0] * x[2] - 2 * Math.Pow(x[3], 2) - 79;
            double f4 = 5 * x[0] - 15 * x[1] + x[2] + 4 * x[3] + 25;
            double[] f12 = { f1, f2, f3, f4 };
            return Vector<double>.Build.DenseOfArray(f12);
        }
        private void btn_bro4_Click(object sender, EventArgs e)
        {
            sw(1);
            ClearForm();
            double[] prArt = {4,4,4,4 };// pradiniai artiniai
            double tiksSiek = 1e-9;
            int iterMax = 100;
            op("Siekiamas tikslumas: " + tiksSiek);

            Vector<double> x = Vector<Double>.Build.DenseOfArray(prArt);
            int n = x.Count;
            op("Pradinis artinys:\n" + x);

            // Pradinio Jakobio matricos artinio apskaiciavimas
            // pradinis A artinys skaiciuojamas pagal skaitinio diferencijavimo formule
            double dx = 0;
            foreach (double item in x)
            {
                dx += Math.Abs(item);
            }
            dx *= 1e-5;
            Vector<double> f0 = f_bro4(x);
            Matrix<double> A = Matrix<double>.Build.Dense(n, n);
            for (int i = 0; i < n; i++)
            {
                Vector<double> x1 = Vector<double>.Build.DenseOfArray(prArt);
                x1[i] = x1[i] + dx;
                Vector<double> f1 = f_bro4(x1);
                Vector<double> f = ((f1 - f0) / dx);
                for (int o = 0; o < n; o++)
                {
                    A[o, i] = f[o];
                }
            }
            op("Pradinio Jakobio matricos artinys:\n" + A);

            // Broideno metodo iteracijos
            Vector<double> ff = f_bro4(x); // pradine funkcijos reikme

            for (int iii = 0; iii < iterMax; iii++)
            {
                Vector<double> deltax = (A * -1).Inverse() * ff;
                Vector<double> x1 = x + deltax;
                Vector<double> ff1 = f_bro4(x1);
                A = A + ((ff1.ToColumnMatrix() - ff.ToColumnMatrix() - A * deltax.ToColumnMatrix()) * deltax.ToRowMatrix()) / (deltax.ToRowMatrix() * deltax.ToColumnMatrix())[0, 0];
                double tikslumas = deltax.Norm(2) / (x.Norm(2) + deltax.Norm(2));
                op("iteracija " + (iii + 1) + " tikslumas " + Math.Round(tikslumas, 11));
                if (tikslumas < tiksSiek)
                {
                    op("Sprendinys x = [" + x[0] + ", " + x[1] + ", " + x[2] + ", " + x[3] + "]");
                    op("Tikslumas = " + tikslumas);
                    break;
                }
                else if (iii == iterMax - 1)
                {
                    op("Tikslumas nepasiektas. Paskutinis artinys x = " + x);
                    break;
                }
                ff = ff1;
                x = x1;
            }

        }
        // optimizavimas
        private double calcLen(Matrix<double> taskai, int i, int p)
        {
            return Math.Sqrt(Math.Pow(taskai[p, 0] - taskai[i, 0], 2) + Math.Pow(taskai[p, 1] - taskai[i, 1], 2));
        }
        private double calcPrice(double len, double kaina)
        {
            return Math.Pow(len - kaina, 2);
        }
        private double tiksloFunc(Matrix<double> taskai, double kaina)
        {
            double suma = 0;
            int n = taskai.RowCount;
            List<string> a = new List<string>();
            for (int i = 0; i < n; i++)
            {
                string kor = taskai[i, 0] + " " + taskai[i, 1];
                for (int p = i; p < n; p++)
                {
                    string korr = taskai[p, 0] + " " + taskai[p, 1];
                    string kor1 = kor + " " + korr;
                    string kor2 = korr + " " + kor;
                    if (!a.Contains(kor1) && !a.Contains(kor2) && kor != korr)
                    {
                        double len = calcLen(taskai, i, p);
                        double price = calcPrice(len, kaina);
                        a.Add(kor1);
                        suma += price;
                    }

                }
            }
            return suma;
        }
        private double tiksloFunc(Vector<double> atstumai, double kaina)
        {
            double suma = 0;

            return suma;
        }
        private void drawPoints(Matrix<double> tskai, Series Fx)
        {
            Fx.Points.Clear();
            int n = tskai.RowCount;
            for (int i = 0; i < n; i++)
            {
                for (int p = 0; p < n; p++)
                {
                    Fx.Points.AddXY(tskai[i, 0], tskai[i, 1]);
                    Fx.Points.AddXY(tskai[p, 0], tskai[p, 1]);
                }
            }
        }
        private void btn_opti_Click(object sender, EventArgs e)
        {
            /*
             * Plokštumoje (−10 ≤ x ≤ 10, −10 ≤ y ≤ 10) išsidėstę n taškų (3 ≤ n ≤ 20). 
             * Kiekvienas taškas su visais kitais yra sujungtas tiesiomis linijomis (stygomis). 
             * Tokios stygos pagaminimo kaina priklauso nuo kelio ilgio l ir užrašoma formule C(l) = (l - a)^2, a € R, a > 0. 
             * Raskite tokias taškų koordinates, kad stygų nutiesimo kaina būtų mažiausia
             */         
            sw(2);
            PreparareForm(-10, 10, -10, 10);
            chart1.Titles.Add("Stygu optimizavimas");
            ClearForm();
            double kaina = 5;
            int iterMax = 100;
            double[,] tsk = {
                { -2, -2},
                { -2,  2},
                {  2, -2},
                {  2,  2},
            };
            Matrix<double> tskai = Matrix<double>.Build.DenseOfArray(tsk);
            int n = tskai.RowCount;
            Series Fx = chart1.Series.Add("F(x)");
            Fx.ChartType = SeriesChartType.Line;
            Fx.Color = Color.Black;
            Fx.BorderWidth = 3;
            double pradineKaina = tiksloFunc(tskai, kaina);
            chart1.Titles.Add(pradineKaina + " -> ");
            drawPoints(tskai, Fx);

            


        }
    }
    

}
