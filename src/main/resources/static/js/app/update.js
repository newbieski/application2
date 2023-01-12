var main = {
    init : function () {
        var _this = this;
        $('#btn-members').on('click', function () {
            _this.members();
        });

        $('#btn-capitalraid').on('click', function () {
            _this.capitalraid();
        });

        $('#btn-clanleaguetaglist').on('click', function () {
            _this.clanleaguetaglist();
        });
        $('#btn-clanleaguewar').on('click', function () {
            _this.clanleaguewar();
        });
        $('#btn-clanleagueattack').on('click', function () {
            _this.clanleagueattack();
        });
        $('#btn-clanwar').on('click', function () {
            _this.clanwar();
        });
    },
    members : function () {
        $.ajax({
            type: 'POST',
            url: '/myclan/current/members',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('클랜원 업데이트 완료');
            window.location.href = '/update';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    capitalraid : function () {
        $.ajax({
            type: 'POST',
            url: '/capitalraid',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('습격전 업데이트 완료');
            window.location.href = '/update';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    clanleaguetaglist : function () {
        $.ajax({
            type: 'POST',
            url: '/clanwarleague/taglist',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('클랜리그 태그 완료');
            window.location.href = '/update';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    clanleaguewar : function () {
        $.ajax({
            type: 'POST',
            url: '/clanwarleague/allwar',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('클랜리그 라운드 업데이트 완료');
            window.location.href = '/update';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    clanleagueattack : function () {
        $.ajax({
            type: 'POST',
            url: '/clanwarleague/allattack',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('클랜리그 공격 업데이트 완료');
            window.location.href = '/update';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    clanwar : function () {
        $.ajax({
            type: 'POST',
            url: '/clanwar',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('클랜전 업데이트 완료');
            window.location.href = '/update';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();