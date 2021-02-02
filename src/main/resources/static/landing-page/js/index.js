// AOS untuk animasi
AOS.init();

// Webshim Library untuk Masalah Currency
webshims.setOptions('forms-ext', {
  replaceUI: 'auto',
  types: 'number'
});
webshims.polyfill('forms forms-ext');

// ===== STYLING UMUM MENGGUNAKAN JQUERY ===== //

// Webshim Library untuk Masalah Currency
webshims.setOptions('forms-ext', {
  replaceUI: 'auto',
  types: 'number'
});
webshims.polyfill('forms forms-ext');

// Perubahan desain pada bagian pengajuan CashAja
$('#prosedur-title-btn').click(function () {
  $(this).addClass('btn-primary').removeClass('d-none')
  $('#prosedur-content').removeClass('d-none')
  $('#prosedur-form').removeClass('d-none')
  $('#syarat-title-btn').removeClass('btn-primary')
  $('#syarat-content').addClass('d-none')
  $('#syarat-img').addClass('d-none')
});


$('#syarat-title-btn').click(function () {
  $(this).addClass('btn-primary').removeClass('d-none')
  $('#syarat-content').removeClass('d-none')
  $('#syarat-img').removeClass('d-none')
  $('#prosedur-title-btn').removeClass('btn-primary')
  $('#prosedur-content').addClass('d-none')
  $('#prosedur-form').addClass('d-none')

  // Cleanup Hover
  clearPengajuan2()
  clearPengajuan3()

  // Reset ke Tahap 1
  $('#pengajuan-1-desc').addClass("black-text")
  $('#pengajuan-1-img').attr("src", "landing-page/images/pengajuan/1-colored.png")
});

$('#pengajuan-1').hover(function () {
  // Step Pengajuan
  $('#pengajuan-1-desc').addClass("black-text")
  $('#pengajuan-1-img').attr("src", "landing-page/images/pengajuan/1-colored.png")
  // Elemen Sebelah Kanan (Form)
  $('#prosedur-form').removeClass('d-none')
  // Hapus State Hover
  clearPengajuan2()
  clearPengajuan3()
});

$('#pengajuan-2').hover(function () {
  // Step Pengajuan
  $('#pengajuan-2-desc').addClass("black-text")
  $('#pengajuan-2-img').attr("src", "landing-page/images/pengajuan/2-colored.png")
  // Elemen Sebelah Kanan (Gambar Tahap 2)
  $('#prosedur-step-2').removeClass('d-none')
  // Hapus State Hover
  clearPengajuan1()
  clearPengajuan3()
});

$('#pengajuan-3').hover(function () {
  // Step Pengajuan
  $('#pengajuan-3-desc').addClass("black-text")
  $('#pengajuan-3-img').attr("src", "landing-page/images/pengajuan/3-colored.png")
  // Elemen Sebelah Kanan (Form)
  $('#prosedur-step-3').removeClass('d-none')
  // Hapus State Hover
  clearPengajuan1()
  clearPengajuan2()
});

// Menghapus Hover Elemen Elemen Sebelumnya Jika Ada Elemen Lain di Hover
function clearPengajuan1() {
  $('#pengajuan-1-desc').removeClass("black-text")
  $('#pengajuan-1-img').attr("src", "landing-page/images/pengajuan/1-uncolored.png")
  $('#prosedur-form').addClass('d-none')
}

function clearPengajuan2() {
  $('#pengajuan-2-desc').removeClass("black-text")
  $('#pengajuan-2-img').attr("src", "landing-page/images/pengajuan/2-uncolored.png")
  $('#prosedur-step-2').addClass('d-none')
}

function clearPengajuan3() {
  $('#pengajuan-3-desc').removeClass("black-text")
  $('#pengajuan-3-img').attr("src", "landing-page/images/pengajuan/3-uncolored.png")
  $('#prosedur-step-3').addClass('d-none')
}

// ===== STYLING ENDS HERE ===== //

// ===== KOMPONEN PENGAJUAN ===== //

// Validasi form pengajuan
(function () {
  'use strict';
  window.addEventListener('load', function () {
    var forms = document.getElementsByClassName('form-pengajuan-submit');
    var validation = Array.prototype.filter.call(forms, function (form) {
      form.addEventListener('submit', function (event) {
        event.preventDefault();
        if (form.checkValidity() === false) {
          event.stopPropagation();
        } else {
          startPengajuan()
        }
        sendClickAction("Kirim Pengajuan")
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();

// Fungsi untuk melakukan pengiriman data klik tombol ke server
function sendClickAction(category) {
  $.ajax({
    url: '/click/add',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    type: "POST",
    data: JSON.stringify({"category": category})
  });
}

// Fungsi untuk klik Prosedur Pengajuan CashAja
$('#prosedur-title-btn').click(function () {
  sendClickAction("Prosedur Pengajuan")
});

// Fungsi untuk klik Prosedur Pengajuan CashAja
$('#syarat-title-btn').click(function () {
  sendClickAction("Syarat Pengajuan")
});

// Fungsi untuk klik Cek Info Lengkap Pengajuan
$('#cek-info-lengkap-pengajuan').click(function () {
  sendClickAction("Cek Info Lengkap Pengajuan")
});

// Fungsi untuk tombol Ajukan Kredit
$('#ajukan-kredit').click(function () {
  window.location.href = '#pengajuan';
  sendClickAction("Ajukan Kredit")
});

// Fungsi untuk tombol Ajukan Topup Express
$('#ajukan-topup-express').click(function () {
  window.location.href = 'https://topup.mtf.co.id:2121/Multiguna/topupmultiguna';
  sendClickAction("Ajukan Top Up Express")
});

// Fungsi untuk tombol Gabung Wira
$('#gabung-wira').click(function () {
  window.location.href = 'https://www.mtf.co.id/id/mandiri-wira';
  sendClickAction("Gabung WIRA")
});

// Fungsi untuk klik Video Testimoni
$('#testimonial-row').click(function () {
  sendClickAction("Testimoni Customer")
});

// Melakukan sembunyikan alert pengajuan sebelum melakukan POST
function preprocessPengajuan() {
  $('#pengajuan-success').addClass('d-none')
  $('#pengajuan-fail').addClass('d-none')
}

// Fungsi melakukan start pengajuan CashAja
function startPengajuan() {
  preprocessPengajuan()
  $.post("/pengajuan/add/", {
    "name": $('#inputName').val(),
    "email": $('#inputEmail').val(),
    "phone": $('#inputPhone').val(),
    "noktp": $('#inputKTP').val(),
    "lokasi": $('#inputDomisili').val(),
    "hubungi": $('#inputKetersediaanWaktu').val()
  }, "json")
    // Pemanggilan AJAX sukses
    .done(function (data) {
      $('#pengajuan-success').removeClass('d-none')
    })
    // Pemanggilan AJAX gagal, umumnya karena masalah sistem & internet
    .fail(function (data) {
      $('#pengajuan-fail').removeClass('d-none')
    });
}

// Mengambil data yang terdapat pada form simulasi
function getSimulationFormValues() {
  let form = {};
  form['product_id'] = "54dd7eb003b811eb9ea5005056a5a40e";
  form['otr'] = $('#inputHarga').val();
  form['acuan_hitung'] = $('#inputAcuanHitung').val();
  form['area'] = $('#inputAreaPlatNomor').val();
  form['fiducia'] = $('#inputAreaTempatTinggal').val();
  form['jenis_asuransi'] = $('#inputJenisAsuransi').val();
  form['dp_persen'] = "30"
  form['tahun_kendaraan'] = $('#inputTahunKendaraan').val();
  return form;
}

// Fungsi untuk memeriksa data dari form simulasi
function checkSimulationFormValues(form) {
  $('#simulasi-fail').addClass('d-none')
  $('#simulasi-process').removeClass('d-none').text('Tunggu sebentar ya, sedang memproses permintaan kamu!')

  if (form['otr'] === "" || form['otr'] === "0") {
    $('#simulasi-fail').removeClass('d-none').text('Harga kendaraan tidak boleh kosong atau bernilai nol')
    $('#simulasi-process').addClass('d-none')
    return false
  } else if (parseInt($('#inputEstimasiPerhitungan').val()) >= form['otr']) {
    $('#simulasi-fail').removeClass('d-none').text('Estimasi perhitungan harus bernilai lebih kecil dari harga barang')
    $('#simulasi-process').addClass('d-none')
    return false
  }
  return true
}

// Kirim Form Pengajuan (Tidak perlu validasi karena sudah ada default value)
function startSimulasi() {

  let form = getSimulationFormValues()

  // Kirim data klik tombol
  sendClickAction("Simulasi Kredit")

  // Apabila terdapat kesalahan form, maka batal dikirimkan
  if (!checkSimulationFormValues(form)) return

  if (form['acuan_hitung'] === "1") {
    form['tdp_diketahui'] = form['otr'] - $('#inputEstimasiPerhitungan').val()
    $.ajax({
      url: 'https://cash-aja-proxy.herokuapp.com/http://mcalc.mtf.co.id:8988/mtf-go/web/index.php/multiguna/api/budget/tdp-diketahui',
      headers: {
        'Token': 'Ex6OF9RJDs09rmNsw_R3v23Ohg6lSDRO',
      },
      type: "POST",
      dataType: 'json',
      contentType: "application/json",
      data: JSON.stringify(form),
      success: function (data) {
        finishSimulationForm(data, form)
      },
      error: function (error) {
        hideSimulationProcessMessage()
        $('#simulasi-fail').removeClass('d-none').text('Data tidak berhasil kami terima. Mohon dicoba kembali.')
      }
    });
  } else {
    form['angsuran_diketahui'] = $('#inputEstimasiPerhitungan').val()
    $.ajax({
      url: 'https://cash-aja-proxy.herokuapp.com/http://mcalc.mtf.co.id:8988/mtf-go/web/index.php/multiguna/api/budget/angsuran-diketahui',
      headers: {
        'Token': 'Ex6OF9RJDs09rmNsw_R3v23Ohg6lSDRO',
      },
      type: "POST",
      dataType: 'json',
      contentType: "application/json",
      data: JSON.stringify(form),
      success: function (data) {
        finishSimulationForm(data, form)
      },
      error: function (error) {
        hideSimulationProcessMessage()
        $('#simulasi-fail').removeClass('d-none').text('Data tidak berhasil kami terima. Mohon dicoba kembali.')
      }
    });
  }
}

// Menampilkan hasil JSON ke antarmuka
function finishSimulationForm(data, form) {
  hideSimulationForm()
  saveSimulationResult(data)
  showSimulationResult("addm")
  showCalculationComponent(form)
  hideSimulationProcessMessage()
}

// Menyembunyikan teks "Mohon Menunggu" yang muncul ketika klik mulai simulasi
function hideSimulationProcessMessage() {
  $('#simulasi-process').addClass('d-none')
}

// Menyembunyikan form simulasi dan mengganti dengan hasil simulasi
function hideSimulationForm() {
  $('#simulasi-container').addClass('d-none')
  $('#hasil-simulasi-container').removeClass('d-none')
}

// Menampilkan form simulasi kembali saat user menekan tombol kembali
function showSimulationForm() {
  $('#simulasi-container').removeClass('d-none')
  $('#hasil-simulasi-container').addClass('d-none')
}

// Menampilkan hasil simulasi dengan melakukan iterasi (BAGIAN REVIEW PILIHAN USER)
function showCalculationComponent(formData) {
  if (formData['acuan_hitung'] === '1') {
    $('#komponen-acuan-perhitungan').text('Pencairan')
  } else {
    $('#komponen-acuan-perhitungan').text('Angsuran')
  }

  if (formData['area'] === '1') {
    $('#komponen-area').text('Sumatera dan Sekitarnya')
  } else if (formData['area'] === '2') {
    $('#komponen-area').text('DKI Jakarta, Banten, dan Jawa Barat')
  } else {
    $('#komponen-area').text('Area Indonesia Lainnya')
  }

  if (formData['fiducia'] === '1') {
    $('#komponen-fiducia').text('NON-DKI')
  } else {
    $('#komponen-fiducia').text('DKI')
  }

  if (formData['jenis_asuransi'] === '1') {
    $('#komponen-jenis-asuransi').text('TLO')
  } else if (formData['area'] === '2') {
    $('#komponen-jenis-asuransi').text('Komprehensif')
  } else {
    $('#komponen-jenis-asuransi').text('Kombinasi')
  }

  $('#komponen-harga-kendaraan').text('Rp. ' + formData['otr'])
  $('#komponen-estimasi-perhitungan').text('Rp. ' + $('#inputEstimasiPerhitungan').val())
  $('#komponen-tahun-kendaraan').text(formData['tahun_kendaraan'])
}

let addb_data = null,
  addm_data = null

// Menyimpan ke variabel global terkait hasil pengambilan data
function saveSimulationResult(data) {
  addb_data = data.data.main.addb
  addm_data = data.data.main.addm
}

// Menampilkan hasil simulasi dengan melakukan iterasi (BAGIAN PEMBUATAN TABEL HASIL PERHITUNGAN)
function showSimulationResult(type) {
  let props = ["tenor", "bulan", "tdp", "suku_bunga", "angsuran"],
    tipe_angsuran = type === "addm" ? addm_data : addb_data

  // Mengosongkan tabel jika user hendak berpindah menu ADDM / ADDB
  $('#simulation-tbody').empty();

  $.each(tipe_angsuran, function (i, data) {
    let tr = $('<tr>');
    $.each(props, function (i, prop) {
      let tableData = null
      if (prop === "tdp" || prop === "angsuran") {
        tableData = "Rp. " + data[prop]
      } else if (prop === "suku_bunga") {
        tableData = data[prop] + " %"
      } else {
        tableData = data[prop]
      }
      $('<td>').html(tableData).appendTo(tr);
    });
    $('#simulation-tbody').append(tr);
    changeSimulationButtonColor(type)
  });
}

// Mengganti warna tombol simulasi
function changeSimulationButtonColor(type) {
  if (type === "addm") {
    // Apabila tombol ADDM diklik oleh pengguna
    $('#addm-section-btn').addClass("btn-primary")
    $('#addb-section-btn').removeClass("btn-primary")
  } else {
    // Apabila tombol ADDB diklik oleh pengguna
    $('#addm-section-btn').removeClass("btn-primary")
    $('#addb-section-btn').addClass("btn-primary")
  }
}
