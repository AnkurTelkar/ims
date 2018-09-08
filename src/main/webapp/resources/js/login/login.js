$(document).ready(function() {
    $('.login').addClass('clicked');
    $('.login').on('submit', function(e) {
        //$('#password').val( hex_sha1( hex_md5( hex_sha1($('#password').val()) ) ) );
        $('.login').removeClass('clicked').addClass('loading');
    });
    $('.resetbtn').on('click', function(e){
        e.preventDefault();
        $('.login').removeClass('loading');
    });
});