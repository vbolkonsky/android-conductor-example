package com.magorasystems.conductor.example.mvp.savecatalog;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.magorasystems.conductor.example.model.CatalogEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */
@Slf4j
public class SaveCatalogPresenterImpl extends MvpBasePresenter<SaveCatalogView>
        implements SaveCatalogPresenter {

    private PublishSubject<CatalogEvent> catalogEventSubject = PublishSubject.create();

    private List<Integer> catalog;

    public SaveCatalogPresenterImpl() {
        catalog = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            catalog.add(i);
        }
        catalogEventSubject
                .doOnSubscribe(disposable -> getChunk(0, 35))
                .subscribe(catalogEvent -> {
                            log.debug("event: {}", catalogEvent);
                            getChunk(catalogEvent.getOffset() + 1, catalogEvent.getLimit());
                        }, throwable -> log.error("event error ", throwable),
                        () -> log.debug("onComplete"));
    }

    @Override
    public void save() {
        getChunk(new ArrayList<>(), 0, 35)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integers -> log.debug("result array {}", integers));
    }


    void getChunk(int page, int limit) {
        int offset = page * limit;
        int size = catalog.size();
        if(size <= offset){
            return;
        }
        log.debug("page: {}, offset: {}, limit: {} ", page, offset, limit);
        if (size >= offset + limit) {
            Observable.just(getSublist(offset, limit))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(integers -> catalogEventSubject.onNext(new CatalogEvent(integers, page, limit)))
                    .subscribe(integers -> log.debug("result array {}", integers));

        } else {
            Observable.just(getSublist(offset,  size - offset))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(integers -> {
                        catalogEventSubject.onNext(new CatalogEvent(integers, page, limit));
                        catalogEventSubject.onComplete();
                    })
                    .subscribe(integers -> log.debug("result array {}", integers));
        }
    }

    Observable<List<Integer>> getChunk(List<Integer> buffer, int page, int limit) {
        int offset = page * limit;
        int size = catalog.size();
        log.debug("page: {}, offset: {}, limit: {}, buffer: {} ", page, offset, limit, buffer);
        if (size >= offset + limit) {
            return Observable.defer(() -> Observable.just(getSublist(offset, limit))
                    .flatMap(integers -> {
                        buffer.addAll(integers);
                        return Observable.just(buffer);
                    })
                    .flatMap(integers -> getChunk(buffer, page + 1, limit)));

        } else {
            return Observable.just(getSublist(offset, size - offset))
                    .flatMap(integers -> {
                        buffer.addAll(integers);
                        return Observable.just(buffer);
                    });
        }
    }

    private List<Integer> getSublist(int offset, int limit) {
        final List<Integer> sublist = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            sublist.add(catalog.get(offset + i));
        }
        return sublist;
    }
}
