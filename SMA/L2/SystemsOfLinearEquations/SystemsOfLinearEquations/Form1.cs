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
        }
        // ----------------------TIESINIU LYGCIU SISTEMU SPRENDIMAS---------------------------------------------------
        // simetrine matrica
        //double[,] A0 = { { 1, -1, 0, 0 }, { -1, 2, -1, 0 }, { 0, -1, 2, -1 }, { 0, 0, -1, 2 } }; // koeficientu matrica
        //double[] b0 = { 2, 0, 0, 0 };// koeficientu vektorius
        // ----------------------
        // singuliari matrica
        //double[,] A0 = { { 1, 1, 1, 1 },  { 1, 1, -1, 1 }, {1, 1, -2, 4 }, { -1, -1, 1, 4 } }; // koeficientu matrica
        //double[] b0 = { 2, 9.1429, 14, -7 }; // nera sprendiniu
        // double[] b0 = { 18, 2, 3, 13 }; // be galo daug sprendiniu
        // ----------------------
        double[,] A0 = { { 1, 2, 3 }, { 3, 4, 5 }, { 6, 5, 3 } }; // koeficientu matrica
        double[] b0 = { 5, 8, 8 };// koeficientu vektorius 
        // ----------------------
        //double[,] A0 = { { 1, 2, 3 }, { 4,5,6 }, { 7,8,9 } }; // singuliari koeficientu matrica
        //double[] b0 = { 29,65,101 };// koeficientu vektorius
        // ----------------------
        //double[,] A0 = { { 1, 1, 1, 1 }, { 1, -1, -1, 1 }, { 2, 1, -1, 2 }, { 3, 1, 2, -1 } }; // koeficientu matrica
        //double[] b0 = { 2, 0, 9, 7 };// koeficientu vektorius
        // ----------------------
        /// <summary>
        ///  Gauso metodas
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button2_Click(object sender, EventArgs e)
        {
            ClearForm();
            // ----------------------Pradiniai duomenys--------------------------
            // matricos generuojamos pagal virsuje aprasytus masyvus
            Matrix<double> A = Matrix<double>.Build.DenseOfArray(A0);
            Vector<double> b = Vector<double>.Build.DenseOfArray(b0);


            // atsitiktiniu matricu sugeneravimas
            //int nrrr = 10;
            //Matrix<double> A = Matrix<double>.Build.Random(nrrr, nrrr);
            //Vector<double> b = Vector<double>.Build.Random(nrrr);

            richTextBox1.AppendText("Pradiniai duomenys: \n");
            richTextBox1.AppendText("A = \n");
            richTextBox1.AppendText(A.ToString());
            richTextBox1.AppendText("b = \n");
            richTextBox1.AppendText(b.ToString());

            // suformuojama isplestine koeficientu matrica
            Matrix<double> Ab = A;
            Ab = Ab.InsertColumn(Ab.ColumnCount, b);
            richTextBox1.AppendText("Isplestine matrica matrica: \n");
            richTextBox1.AppendText("Ab = \n");
            richTextBox1.AppendText(Ab.ToString());

            //-----------------------------------------------------
            // Gauso metodo veiksmai
            Vector<double> x = Vector<double>.Build.Dense(Ab.RowCount);


            //-----------------------------------------------------

            // patikrinimas

            richTextBox1.AppendText("x = \n");
            richTextBox1.AppendText(x.ToString());
            richTextBox1.AppendText("patikrinimas A0*x-b0 = \n");
            richTextBox1.AppendText((A * x - b).ToString("#0.00e+0\t"));


        }
        double[,] CHA = { { 64, 16, 32, 8 }, { 16, 8, 0, 0}, {32, 0, 48, 0}, {8, 0, 0, 10} }; // koeficientu matrica
        double[] CHB = { 256, 48, 112, 96 };// koeficientu vektorius 
        private void btn_choleskio_Click(object sender, EventArgs e)
        {
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
            for (int eil = 0; eil < n; eil++)
            {
                if (eil != 0) {
                    b[eil] = (b[eil] - (A.SubMatrix(0, eil, eil, 1).Transpose() * b.SubVector(0, eil)).Sum()) / A[eil, eil];
                }
                else
                    b[eil] = (b[eil]) / A[eil, eil];
                op(b);
            }

            // 2-as atvirkstinis zingsnis   Ux=b,  x->b
            for (int eil = n-1; eil >= 0; eil--)
            {
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
        private void op(Object x)
        {
            richTextBox1.AppendText(x.ToString() + '\n');
        }
    }
    

}
