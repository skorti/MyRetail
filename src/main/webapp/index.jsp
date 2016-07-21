<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body>

<h2>My Retail</h2>

<button id="find-button" onclick="$(this).trigger('find-product'); return false;">Find</button>
<label for="find-product-id-input"> Id </label>
<input id="find-product-id-input"  type="text"/>

<br>
<br>

<button id="update-button" onclick="$(this).trigger('update-product'); return false;">Update</button>
<label for="update-product-id"> Id </label>
<input id="update-product-id" type="text"/>
<label for="update-product-value-input">Value</label>
<input id="update-product-value-input" type="text"/>

<br>
<br>

<div id="content"></div>
</body>

<script>
    (function  ($) {

        $(this).on('find-product', function (evt) {

            var id = $('#find-product-id-input').val();
            if(!isIntValue(id)){
                alert('Numeric value required for the product id');
                return;
            }

            var url= 'skorti/services/products/'+id;
            $.get(url, function (response) {
                $('#content').html(JSON.stringify(response));
            });

        });



        $(this).on('update-product', function (evt) {
            var id = $('#update-product-id').val();
            var value = $('#update-product-value-input').val();

            if(!isIntValue(id) || !isIntValue(value)){
                alert('Numeric value required for the product ID and Value');
                return;
            }

            var jsonObj = {
                "id":id,
                "value":value
            };

            $.ajax({
                url: 'skorti/services/products/'+id,
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(jsonObj),
                success: function (response) {
                    $('#content').html('Rows updated:' + response);
                },
                error: function (response) {
                    $('#content').html(response.responseText);
                }
            });

            function isIntValue(n){
                return $.isNumeric(n) && parseInt(n, 10) > 0;
            }

        });
    })(jQuery);
</script>
</html>
