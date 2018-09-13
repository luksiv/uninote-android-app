using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Factorization;

namespace Pvz1
{
    public partial class Form1 : Form
    {
        List<Timer> Timerlist = new List<Timer>();

        public Form1()
        {
            InitializeComponent();
            Initialize();
        }

        // ---------------------------------------------- PUSIAUKIRTOS METODAS ----------------------------------------------

        float x1, x2, xtemp; // izoliacijos intervalo pradžia ir galas, vidurio taškas
        int N = 1000; // maksimalus iteracijų skaičius
        int iii; // iteracijos numeris

        Series Fx, X1X2, XMid; // naudojama atvaizduoti f-jai, šaknų rėžiams ir vidiniams taškams


        /// <summary>
        /// Sprendžiama lygtis F(x) = 0
        /// </summary>
        /// <param name="x">funkcijos argumentas</param>
        /// <returns></returns>
        private double F(double x)
        {
            return (double)(Math.Sin(x));
        }


        // Mygtukas "Pusiaukirtos metodas" - ieškoma šaknies, ir vizualizuojamas paieškos procesas
        private void button3_Click(object sender, EventArgs e)
        {
            ClearForm(); // išvalomi programos duomenys
            PreparareForm(0, 10, -2, 2);
            x1 = 2; // izoliacijos intervalo pradžia
            x2 = 5; // izoliacijos intervalo galas
            iii = 0; // iteraciju skaičius
            richTextBox1.AppendText("Iteracija         x            F(x)        x1          x2          F(x1)         F(x2)       \n");
            // Nubraižoma f-ja, kuriai ieskome saknies
            Fx = chart1.Series.Add("F(x)");
            Fx.ChartType = SeriesChartType.Line;
            double x = 0;
            for (int i = 0; i < 50; i++)
            {
                Fx.Points.AddXY(x, F(x));  x = x + (2 * Math.PI) /50;
            }
            Fx.BorderWidth = 3;
            
            X1X2 = chart1.Series.Add("X1X2");
            X1X2.MarkerStyle = MarkerStyle.Circle;
            X1X2.MarkerSize = 8;
            X1X2.ChartType = SeriesChartType.Point;
            X1X2.ChartType = SeriesChartType.Line;


            XMid = chart1.Series.Add("XMid");
            XMid.MarkerStyle = MarkerStyle.Circle;
            X1X2.ChartType = SeriesChartType.Point;
            X1X2.ChartType = SeriesChartType.Line;
            XMid.MarkerSize = 8;

            timer2.Enabled = true;
            timer2.Interval = 500; // timer2 intervalas milisekundemis
            timer2.Start();           
        }
        

        /// <summary>
        /// timer2 iteracijoje atliekami veiksmai
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer2_Tick(object sender, EventArgs e)
        {
            xtemp = (x1 + x2) / 2; // apskaiciuojamas vidurinis taskas


            if (Math.Abs(F(xtemp)) > 1e-6 & iii <= N)
            // tikrinama salyga, ar funkcijos absoliuti reiksme daugiau uz nustatyta (norima) 
            // tiksluma ir nevirsytas maksimalus iteraciju skaicius
            {
                X1X2.Points.Clear();
                XMid.Points.Clear();

                X1X2.Points.AddXY(x1, 0);
                X1X2.Points.AddXY(x2, 0);
                XMid.Points.AddXY(xtemp, 0);

                richTextBox1.AppendText(String.Format(" {0,6:d}   {1,12:f7}  {2,12:f7} {3,12:f7} {4,12:f7} {5,12:f7} {6,12:f7}\n",
                    iii, xtemp, F(xtemp), x1, x2, F(x1), F(x2)));
                if (Math.Sign((double)F(x1)) != Math.Sign((double)F(xtemp)))
                {
                    x2 = xtemp;
                }
                else
                {
                    x1 = xtemp;
                }
                iii = iii + 1;

            }
            else
            // skaiciavimai stabdomi
            {
                richTextBox1.AppendText("Skaičiavimai baigti");
                timer2.Stop();
            }
        }


        // ---------------------------------------------- PARAMETRINĖS FUNKCIJOS ----------------------------------------------

        List<PointF> data = new List<PointF>(); 
        Series S1;

        /// <summary>
        /// Parametrinis interpoliavimas
        /// </summary>
        private void button5_Click(object sender, EventArgs e)
        {
            ClearForm(); // išvalomi programos duomenys
            PreparareForm(-10, 10, -10, 10);
            data.Clear();
            // apskaičiuojamos funkcijos reikšmės
            for (int i = 0; i < 400; i++)
            {
                float x = i / 50f * (float)(Math.Sin(2*i / 10f));
                float y = i / 50f * (float)(Math.Sin(i / 10f));
                data.Add(new PointF(x, y));
            }
            S1 = chart1.Series.Add("S1");
            S1.BorderWidth = 9;
            S1.ChartType = SeriesChartType.Line;
          
            timer3.Enabled = true;
            timer3.Interval = 15;
            timer3.Start();
        }


        private void timer3_Tick(object sender, EventArgs e)
        {
            Series S1 = chart1.Series[0];
            int pointsSoFar = S1.Points.Count;
            if (pointsSoFar < data.Count)
            {
                S1.Points.AddXY(data[pointsSoFar].X, data[pointsSoFar].Y);
            }
            else
            {
                timer1.Stop();
            }
        }

        // ---------------------------------------------- TIESINĖ ALGEBRA ----------------------------------------------

        /// <summary>
        /// Tiesine algebra (naudojama MathNet)
        /// </summary>
        private void button2_Click(object sender, EventArgs e)
        {
            ClearForm();

            double[,] x = { { 1, 2, 3 }, { 3, 4, 5 }, { 6, 5, 8 } };
            // iš masyvo sugeneruoja matricą, is matricos išskiria eilutę - suformuoja vektorių
            Matrix<double> m = Matrix<double>.Build.DenseOfArray(x);
            Vector<double> v = m.Row(1);
            richTextBox1.AppendText("\nMatrica m:\n");
            richTextBox1.AppendText(m.ToString());

            richTextBox1.AppendText("\nVektorius v:\n");
            richTextBox1.AppendText(v.ToString());

            richTextBox1.AppendText("\ntranspose(m):\n");
            richTextBox1.AppendText(m.Transpose().ToString());

            Matrix<double> vm = v.ToRowMatrix();
            richTextBox1.AppendText("\nvm = v' - toRowMatrix()\n");
            richTextBox1.AppendText(vm.ToString());

            Vector<double> v1 = m * v;
            richTextBox1.AppendText("\nv1 = m * v\n");
            richTextBox1.AppendText(v1.ToString());
            richTextBox1.AppendText("\nmin(v1)\n");
            richTextBox1.AppendText(v1.Min().ToString());

            Matrix<double> m1 = m.Inverse();
            richTextBox1.AppendText("\ninverse(m)\n");
            richTextBox1.AppendText(m1.ToString());

            richTextBox1.AppendText("\ndet(m)\n");
            richTextBox1.AppendText(m.Determinant().ToString());

            // you must add reference to assembly system.Numerics
            Evd<double> eigenv = m.Evd();
            richTextBox1.AppendText("\neigenvalues(m)\n");
            richTextBox1.AppendText(eigenv.EigenValues.ToString());
            
            LU<double> LUanswer = m.LU();
            richTextBox1.AppendText("\nMatricos M LU skaida\n");
            richTextBox1.AppendText("\nMatrica L:\n");
            richTextBox1.AppendText(LUanswer.L.ToString());
            richTextBox1.AppendText("\nMatrica U:\n");
            richTextBox1.AppendText(LUanswer.U.ToString());
            
            QR<double> QRanswer = m.QR();
            richTextBox1.AppendText("\nMatricos M QR skaida\n");
            richTextBox1.AppendText("\nMatrica Q:\n");
            richTextBox1.AppendText(QRanswer.Q.ToString());
            richTextBox1.AppendText("\nMatrica R:\n");
            richTextBox1.AppendText(QRanswer.R.ToString());

            Vector<double> v3 = m.Solve(v);
            richTextBox1.AppendText("\nm*v3 = v sprendziama QR metodu\n");
            richTextBox1.AppendText(v3.ToString());
            richTextBox1.AppendText("Patikrinimas\n");
            richTextBox1.AppendText((m * v3 - v).ToString());
            
        }
        

        // ---------------------------------------------- KITI METODAI ----------------------------------------------

        /// <summary>
        /// Uždaroma programa
        /// </summary>
        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
        
        /// <summary>
        /// Išvalomas grafikas ir consolė
        /// </summary>
        private void button4_Click(object sender, EventArgs e)
        {
            ClearForm();
        }
        

        public void ClearForm()
        {
            richTextBox1.Clear(); // isvalomas richTextBox1
            // sustabdomi timeriai jei tokiu yra
            foreach (var timer in Timerlist)
            {
                timer.Stop();
            }

            // isvalomos visos nubreztos kreives
            chart1.Series.Clear();
        }
    }
}
