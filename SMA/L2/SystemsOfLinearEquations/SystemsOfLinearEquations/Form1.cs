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
    }
}
